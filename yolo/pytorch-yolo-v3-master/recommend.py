#잠재요인 협업 필터링을 이용한 레시피 추천
from sqlalchemy import create_engine
from sklearn.decomposition import TruncatedSVD
from scipy.sparse.linalg import svds
import pymysql

import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
import warnings
import random
warnings.filterwarnings("ignore")
    
engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.151.180/pytest?charset=utf8', convert_unicode=True, echo = False)
conn = engine.connect()

connect = pymysql.connect(host='13.125.151.180', user='hello', password='Csedbadmin!1', db='pytest')
curs = connect.cursor()

rating_data = pd.read_sql_table('recipeScore', conn)
recipe_data = pd.read_sql_table('recipe', conn)
ingredient_data = pd.read_sql_table('ingredients', conn)
recipe_data.drop('data', axis=1, inplace=True)
recipe_data.drop('process', axis=1, inplace=True)

df_user_recipe_ratings = rating_data.pivot (
    index='email',
    columns='recipeName',
    values='score'
).fillna(0)

# matrix는 pivot_table 값을 numpy matrix로 만든 것
matrix = df_user_recipe_ratings.values

# user_ratings_mean은 사용자의 평균 평점
user_ratings_mean = np.mean(matrix, axis = 1)

# R_user_mean : 사용자-레시피에 대해 사용자 평균 평점을 뺀 것.
matrix_user_mean = matrix - user_ratings_mean.reshape(-1, 1)

# scipy에서 제공해주는 svd.
# U 행렬, sigma 행렬, V 전치 행렬을 반환.

U, sigma, Vt = svds(matrix_user_mean, k = 7)

sigma = np.diag(sigma)

# U, Sigma, Vt의 내적을 수행하면, 다시 원본 행렬로 복원이 된다.
# 거기에 + 사용자 평균 rating을 적용한다.
svd_user_predicted_ratings = np.dot(np.dot(U, sigma), Vt) + user_ratings_mean.reshape(-1, 1)

df_svd_preds = pd.DataFrame(svd_user_predicted_ratings, index=df_user_recipe_ratings.index, columns = df_user_recipe_ratings.columns)


#추천 함수
def recommend_recipes(df_svd_preds, user_email, foodName,  ori_recipes_df, ori_ratings_df, ori_ingredient_df, num_recommendations=5):
    userlist = ori_ratings_df.email

    ## 평가데이터가 없는 유저일 경우 다른 무작위 유저의 추천 정보를 띄운다.
    #유저 리스트를 추출
    userlist = ori_ratings_df.drop_duplicates(subset='email')
    userlist = userlist.reset_index(drop=True)
    #무작위 유저 선정
    if ori_ratings_df[ori_ratings_df.email == user_email].shape[0] == 0:
        user_email = userlist.email[random.randrange(userlist.email.size)]

    ##추천과정
    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다.
    user_data = ori_ratings_df[ori_ratings_df.email == user_email]

    # 위에서 뽑은 user_data와 원본 레시피 데이터를 합친다.
    user_history = user_data.merge(ori_recipes_df, on = 'recipeName').sort_values(['score'], ascending=False)

    # 원본 레시피 데이터에서 사용자가 평가한 레시피 데이터를 제외한 데이터를 추출
    recommendations = ori_recipes_df[~ori_recipes_df['recipeName'].isin(user_history['recipeName'])]

    # 최종적으로 만든 pred_df에서 사용자 index에 따라 레시피 데이터 정렬 -> 레시피 평점이 높은 순으로 정렬 됌
    sorted_user_predictions = df_svd_preds.loc[user_email].sort_values(ascending=False)

    # 사용자의 레시피 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다.
    recommendations = recommendations.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'recipeName')
    # 컬럼 이름 바꾸고 정렬해서 return
    recommendations = recommendations.rename(columns = {user_email: 'Predictions'}).sort_values('Predictions', ascending = False)
    #index 재정렬
    recommendations = recommendations.reset_index(drop=True)

    ## 이미 평가된 레시피에 대해서도 추천리스트를 생성
    recommended = user_history.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'recipeName')
    # 점수가 -1인 경우는 제외
    recommended = recommended[recommended.score != -1]
    recommended = recommended.rename(columns = {user_email: 'Predictions'}).sort_values('Predictions', ascending = False)
    recommended = recommended.reset_index(drop=True)


    ##foodName이 들어가는 레시피를 추려낸다
    in_re_df = list()
    recomm = ""
    alreay_recomm = ""
    for i in foodName:
        in_re_df.append(ingredient_data[ingredient_data.foodName == i])

    df_in = pd.core.frame.DataFrame()
    for i in range(0,len(in_re_df)):
        df_in = pd.concat([df_in, in_re_df[i]])
    if df_in.shape[0] == 0:
        #아무 재료가 없을 경우, 전체 추천 목록 중에서 추천
        recomm = recommendations.iloc[:num_recommendations, :]
        already_recomm = recommended.iloc[:num_recommendations, :]
    else:
        #
        recipe_ingredient_df = df_in.merge(recipe_data, on = 'recipeName')
        recomm = recipe_ingredient_df.merge(recommendations, on = 'recipeName').sort_values('Predictions', ascending = False).iloc[:num_recommendations, :]
        recomm = recomm.reset_index(drop=True)
        #
        already_recomm = recipe_ingredient_df.merge(recommended, on = 'recipeName').sort_values('Predictions', ascending = False).iloc[:num_recommendations, :]
        already_recomm = already_recomm.reset_index(drop=True)

    sql = "delete from recommendRecipe where email=%s"
    curs.execute(sql, user_email)


    #추천레시피를 num_recommendations만큼 db에 삽입
    sql = "insert into recommendRecipe values(%s, %s)"
    for i in range(recomm.shape[0]):
        curs.execute(sql, (user_email,recomm.recipeName[i]))

    #추천레시피가 num_recommendations만큼 안될경우 이미 평가된 항목에서 추천
    if(recomm.shape[0] < num_recommendations):
        for i in range(num_recommendations-recomm.shape[0]):
            curs.execute(sql, (user_email,already_recomm.recipeName[i]))

    connect.commit()

#usage
recommend_recipes(df_svd_preds, '27', [] , recipe_data, rating_data, ingredient_data)
