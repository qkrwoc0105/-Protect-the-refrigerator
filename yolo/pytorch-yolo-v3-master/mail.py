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
    
engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.102.154/pytest', echo = False)

conn = pymysql.connect(host='13.125.102.154', user='hello', password='Csedbadmin!1', db='pytest')
curs = conn.cursor()

preTime = datetime.now()
try:
    while True:
        nowTime = datetime.now()
        print(preTime, nowTime)
            
        # 냉장고 내의 음식의 유통기한이 임박, 초과하면 주인들에게 메일
        # 카메라 마다 최신 사진을 찾은 뒤, 식품의 유통기한과 넣은 시간을 현재 시간과 비교 
        sql = "select * from camera"
        curs.execute(sql)
        rows = curs.fetchall()
        print(rows)
        
        for row in rows:      
            sql = "select photoID from photo where cameraID = '" + row[0] + "'"
            curs.execute(sql)
            photos = curs.fetchall()
            print(photos)
            
            late = ' '
            if len(photos) != 0:
                late = photos[0][0];       
                late_split = late.split('_') 
                late_time = datetime(int(late_split[0]), int(late_split[1]), int(late_split[2]), int(late_split[3]), int(late_split[4]), int(late_split[5]))
                for photo in photos :
                    photo_split = photo[0].split('_')
                    photo_time = datetime(int(photo_split[0]), int(photo_split[1]), int(photo_split[2]), int(photo_split[3]), int(photo_split[4]), int(photo_split[5]))
                    if late_time < photo_time :
                        late = photo[0]
                        late_split = late.split('_')
                        late_time = datetime(int(late_split[0]), int(late_split[1]), int(late_split[2]), int(late_split[3]), int(late_split[4]), int(late_split[5]))
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
                        if diff > shelfLife: # 보낸 기록 확인 추가
                            title = '유통기한이 초과 및'
                            text = text + refrigerator[0][2] + '냉장고의 ' + foodName + '식품의 유통기한 초과! \n'
                            alertFoods.append(foodName)
                        # 유통기한 임박 시 메일
                        elif diff >= shelfLife - 3 or shelfLife < 3 : # 보낸 기록 확인 추가
                            title = '유통기한이 임박 및'
                            text = text + refrigerator[0][2] + '냉장고의 ' + foodName + '식품의 유통기한 임박! \n'  
                            alertFoods.append(foodName)                            
                        
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

                        sql = "delete from photographedFood where photoID = '" + photoID + "'"
                        curs.execute(sql)
                        conn.commit()

                        sql = "delete from photo where photoID = '" + photoID + "'"
                        curs.execute(sql)
                        conn.commit()

                        filename = glob('detectplate.py')
                        subprocess.call(['python', filename, '--images', 'imgs/' + tempname, '--det', 'det', '--photoID', photoID, '--cameraID', cameraID])

                        buffer = BytesIO()
                        im = Image.open('det/det_' + tempname)

                        im.save(buffer, format='jpeg')
                        img_str2 = base64.b64encode(buffer.getvalue())

                        img_df = pd.DataFrame({'photoID':[photoID], 'data':[img_str], 'analyzedData':[img_str2], 'cameraID':[cameraID]})
                        img_df.to_sql('photo', con=engine, if_exists='append', index=False)
                        # 레시피를 받아옴
                        # text에 레시피 추천 추가
                        text = text + '오늘의 추천 레시피입니다.\n'
                        for recipe in recipes:
                            text = text + recipe[0] + ': http://project/recipe/' + recipe[0] + '\n'
                        title = title + ' 레시피 추천 메일입니다.'
                        sendEmail(title, text, email)
                
        # images 하루 한 번 전체 삭제
        if nowTime.hour == 0 and preTime.hour == 23:
            sql = "delete from images"
            curs.execute(sql)
            conn.commit()
            
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
                conn.commit()
        
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
                conn.commit()
                
        preTime = nowTime
        
        # 1분에 한번 검사
        time.sleep(60)
except KeyboardInterrupt:
       pass
smtp.quit()
conn.close()