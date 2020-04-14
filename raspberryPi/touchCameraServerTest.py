import RPi.GPIO as g
import picamera
import time

import pandas as pd
import pymysql
from sqlalchemy import create_engine
from PIL import Image
import base64
from io import BytesIO
import socket

g.setmode(g.BCM)
KEY = 24;
g.setup(KEY, g.IN)

s = socket.socket()         
host = '192.168.137.213'
port = 1234               
s.bind((host, port))       
s.listen(5)

filename = 'test.jpg'
raspberryPiID = 'applemango'
cameraID = 'camera1'

while True:
    c, addr = s.accept()
    if not c:
        continue
    else:
        print ('Got connection from', addr)
        break

try:
    while 1:
        if g.input(KEY):
            print('capture')
            with picamera.PiCamera() as camera:
                camera.resolution=(256, 192)
                camera.capture(filename)
                
            dte = time.localtime()
            Year = dte.tm_year
            Mon = dte.tm_mon
            Day = dte.tm_mday
            WDay = dte.tm_wday
            Hour = dte.tm_hour
            Min = dte.tm_min
            Sec = dte.tm_sec
            photoID = str(Year) + '_' + str(Mon) + '_' + str(Day) + '_' + str(Hour) + '_' + str(Min) + '_' + str(Sec);
                
            engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.102.154/pytest', echo = False)
            buffer = BytesIO()
            im = Image.open(filename)

            im.save(buffer, format='jpeg')
            img_str = base64.b64encode(buffer.getvalue())
            
            img_df = pd.DataFrame({'data':[img_str]})
            img_df.to_sql('images', con=engine, if_exists='append', index=False)
            
            answer = raspberryPiID + ' ' + cameraID + ' ' + photoID
            print(answer)
            c.send(answer.encode())
except KeyboardInterrupt:
    pass;
c.close
g.cleanup()
