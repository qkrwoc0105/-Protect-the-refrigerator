import smtplib
from email.mime.text import MIMEText
from datetime import datetime, timedelta
import time
import pymysql

import pandas as pd
from sqlalchemy import create_engine
from PIL import Image
import base64
from io import BytesIO

from glob import glob
import subprocess
import sys

from sklearn.decomposition import TruncatedSVD
from scipy.sparse.linalg import svds

import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import warnings
import random

googleID = 'applemangoHansung@gmail.com'
googlePW = 'applemango!@#123'

smtp = smtplib.SMTP('smtp.gmail.com', 587) 
smtp.ehlo()      
smtp.starttls()  
smtp.login(googleID, googlePW)

def sendEmail(title, text, sendID = 'cjfwoqkr0105@gmail.com'):
    msg = MIMEText(text)
    msg['Subject'] = title
    msg['To'] = sendID
    smtp.sendmail(googleID, sendID, msg.as_string())
    
engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.151.180/pytest', echo = False)
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
def recommend_recipes(df_svd_preds, ori_user_email, foodName,  ori_recipes_df, ori_ratings_df, ori_ingredient_df, num_recommendations=5):
    userlist = ori_ratings_df.email
    user_email = ori_user_email

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
    curs.execute(sql, ori_user_email)


    #추천레시피를 num_recommendations만큼 db에 삽입
    sql = "insert into recommendRecipe values(%s, %s)"
    for i in range(recomm.shape[0]):
        curs.execute(sql, (ori_user_email,recomm.recipeName[i]))

    #추천레시피가 num_recommendations만큼 안될경우 이미 평가된 항목에서 추천
    if(recomm.shape[0] < num_recommendations):
        for i in range(num_recommendations-recomm.shape[0]):
            curs.execute(sql, (ori_user_email,already_recomm.recipeName[i]))

    connect.commit()
    
preTime = datetime.now()
try:
    while True:
        nowTime = datetime.now()
        print(preTime, nowTime)
            
        # 6,11,16시 하루 세번 메일
        if (nowTime.hour == 6 and preTime.hour == 5) or (nowTime.hour == 11 and preTime.hour == 10) or (nowTime.hour == 16 and preTime.hour == 15) or 1:
        # 카메라 마다 최신 사진을 찾은 뒤, 식품의 유통기한과 넣은 시간을 현재 시간과 비교 
            sql = "select * from camera"
            curs.execute(sql)
            rows = curs.fetchall()
            print(rows)
            
            for row in rows:     
                sql = "select photoID from photo where cameraID = %s order by photoID desc limit 1"
                curs.execute(sql, row[0])
                photos = curs.fetchall()
                
                late = photos[0][0]            
                print(late)
                
                sql = "select * from refrigerator where raspberryPiID = '" + row[1] + "'"
                curs.execute(sql)
                refrigerator = curs.fetchall()
                print(refrigerator)
                
                sql = "select * from possession where raspberryPiID = '" + refrigerator[0][0] + "'"
                curs.execute(sql)
                possessions = curs.fetchall()
                print(possessions)
                
                for possession in possessions:
                    email = possession[0]
                    
                    sql = "select * from members where email = '" + email + "'"
                    curs.execute(sql)
                    member = curs.fetchall()
                    print(member)
                    
                    if late != ' ':
                        sql = "select * from photographedFood where photoID = '" + late + "'"
                        curs.execute(sql)
                        foods = curs.fetchall()
                        print(foods)
                        
                        title = ''
                        text = ''
                        alertFoods = []
                        for food in foods:
                            foodName = food[2]
                            shelfLife = food[3]
                            inday_split = food[4].split('_')
                            inTime = datetime(int(inday_split[0]), int(inday_split[1]), int(inday_split[2]), int(inday_split[3]), int(inday_split[4]), int(inday_split[5]))
                            diff = (nowTime-inTime).days
                            # 유통기한 초과 시 메일
                            print(diff, shelfLife)
                            if diff > shelfLife: # 보낸 기록 확인 추가
                                title = '유통기한 경고와'
                                text = text + refrigerator[0][2] + '냉장고의 ' + foodName + '식품의 유통기한 초과! \n'
                                alertFoods.append(foodName)
                            # 유통기한 임박 시 메일
                            elif diff >= shelfLife - 3 or shelfLife < 3 : # 보낸 기록 확인 추가
                                title = '유통기한 경고와'
                                text = text + refrigerator[0][2] + '냉장고의 ' + foodName + '식품의 유통기한 임박! \n'  
                                alertFoods.append(foodName)      
                            print(title)
                            
                        text = text + 'http://13.125.151.180:8080/gdProject2/infridge\n\n'
                        
                        if title != '':
                            # yolo실행
                            raspberryPiID = row[1]
                            cameraID = row[0]
                            photoID = late

                            img_df = pd.read_sql(sql="select * from photo where photoID = '" + photoID + "'", con=engine)
                            img_str = img_df['data'].values[0]
                            img = base64.decodebytes(img_str)
                            im = Image.open(BytesIO(img))

                            tempname = 'temp.jpg'
                            im.save('imgs/' + tempname)

                            sql = "delete from photo where photoID = '" + photoID + "'"
                            curs.execute(sql)
                            connect.commit()

                            filename = glob('detectplate.py')
                            subprocess.call(['python', filename, '--images', 'imgs/' + tempname, '--det', 'det', '--photoID', photoID, '--cameraID', cameraID])

                            buffer = BytesIO()
                            im = Image.open('det/det_' + tempname)

                            im.save(buffer, format='jpeg')
                            img_str2 = base64.b64encode(buffer.getvalue())

                            img_df = pd.DataFrame({'photoID':[photoID], 'data':[img_str], 'analyzedData':[img_str2], 'cameraID':[cameraID]})
                            img_df.to_sql('photo', con=engine, if_exists='append', index=False)
                            
                        #경고할 음식이 없으면 냉장고의 음식을 넣음
                        if alertFoods == []:
                            sql = "select * from photographedFood where photoID = '" + late + "'"
                            curs.execute(sql)
                            foods = curs.fetchall()
                            for food in foods:
                                alertFoods.append(food[2])
                               
                        print(email, alertFoods)
                        # 레시피 추천 함수
                        recommend_recipes(df_svd_preds, email, alertFoods, recipe_data, rating_data, ingredient_data)
                         
                        sql = "select recipeName from recommendRecipe where email = '" + email + "'"
                        curs.execute(sql)
                        recipes = curs.fetchall()
                        print(recipes)

                        text = text + '오늘의 추천 레시피입니다.\n\n'
                        for recipe in recipes:
                            text = text + recipe[0] + '\n'
                        title = title + ' 레시피 추천 메일입니다.'
                        text = text + 'http://13.125.151.180:8080/gdProject2/totalRecommend'

                        sendEmail(title, text, email)
                
        # images 하루 한 번 전체 삭제
        if nowTime.hour == 0 and preTime.hour == 23:
            sql = "delete from images"
            curs.execute(sql)
            connect.commit()
            
        # eatenFood 24시간이 지난 음식 삭제
        sql = "select * from eatenFood"
        curs.execute(sql)
        rows = curs.fetchall()

        for row in rows:
            photoID = row[0]
            row_split = photoID.split('_')
            row_time = datetime(int(row_split[0]), int(row_split[1]), int(row_split[2]), int(row_split[3]), int(row_split[4]), int(row_split[5]))
            if (nowTime - row_time).days > 0:
                sql = "delete from eatenFood where photoID = '" + photoID + "'"
                curs.execute(sql)
                connect.commit()
        
        # photo 테이블에 저장한지 30일 초과한 data와 analyzedData 삭제
        sql = "select * from photo"
        curs.execute(sql)
        rows = curs.fetchall()
        
        for row in rows:
            photoID = row[0]
            row_split = photoID.split('_')
            row_time = datetime(int(row_split[0]), int(row_split[1]), int(row_split[2]), int(row_split[3]), int(row_split[4]), int(row_split[5]))
            if (nowTime - row_time).days > 30:
                sql = "update photo set data = 'NULL', analyzedData = 'NULL' where = '" + photoID + "'"
                curs.execute(sql)
                connect.commit()
                
        preTime = nowTime
        
        # 1분에 한번 검사
        time.sleep(60)
except KeyboardInterrupt:
       pass
smtp.quit()
connect.close()