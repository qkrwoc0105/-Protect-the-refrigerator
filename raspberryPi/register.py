import pymysql

conn = pymysql.connect(host='13.125.102.154', user='hello', password='Csedbadmin!1', db='pytest')
curs = conn.cursor()

raspberryPiID = input("라즈베리파이의 ID를 입력: ")
raspberryPiPW = input('비밀번호를 입력: ')
name = input('냉장고 이름을 입력: ')
location = input('냉장고의 위치를 입력: ')
cameraID = input('카메라의 ID를 입력: ')

sql = "insert into refrigerator values(%s, %s, %s, %s)"
curs.execute(sql, (raspberryPiID, raspberryPiPW, name, location))
conn.commit()

sql = "insert into camera values(%s, %s)"
curs.execute(sql, (cameraID, raspberryPiID))
conn.commit()

filename = 'info.txt'
with open(filename, mode = 'w') as f:
    f.write(raspberryPiID + ' ' + cameraID)
    
with open(filename, mode = 'r') as f:
    print(f.read())
