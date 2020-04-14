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

s = socket.socket()        
host = '192.168.137.1'
port = 8888               
s.connect((host, port))

filename = 'test.jpg'

while True:
    answer = input("1: capture, 2: exit  ")
    
    if answer == '1':
        with picamera.PiCamera() as camera:
            camera.resolution=(1024, 768)
            camera.capture(filename)
            
#        engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.102.154/pytest', echo = False)
#        buffer = BytesIO()
#        im = Image.open(filename)

#        im.save(buffer, format='jpeg')
#        img_str = base64.b64encode(buffer.getvalue())

#        img_df = pd.DataFrame({'data':[img_str]})
#        img_df.to_sql('images', con=engine, if_exists='append', index=False)
    elif answer == '2':
        break
    else :
        continue
    s.send(answer.encode())
