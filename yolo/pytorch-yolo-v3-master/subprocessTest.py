import pandas as pd
import pymysql
from sqlalchemy import create_engine
from PIL import Image
import base64
from io import BytesIO

from glob import glob
import subprocess

import socket               # Import socket module

s = socket.socket()         # Create a socket object
host = '192.168.137.213'
port = 1234                # Reserve a port for your service.
s.connect((host, port))

engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.102.154/pytest', echo = False)

while True:
    print('ready')
    recvData = s.recv(1024).decode()
    dataSplit = recvData.split()
    cameraID = dataSplit[1]
    photoID = dataSplit[2]

    if len(dataSplit) > 1:
        num_df = pd.read_sql(sql="select max(num) from images", con=engine)
        num = num_df.values[0][0]

        img_df = pd.read_sql(sql="select * from images where num = " + str(num), con=engine)
        img_str = img_df['data'].values[0]
        img = base64.decodebytes(img_str)
        im = Image.open(BytesIO(img))
        
        tempname = 'temp.jpg'
        im.save('imgs/' + tempname)

        filename = glob('detectplate.py')
        subprocess.call(['python', filename, '--images', 'imgs/' + tempname, '--det', 'det', '--photoID', photoID, '--cameraID', cameraID])
        
        buffer = BytesIO()
        im = Image.open('det/det_' + tempname)

        im.save(buffer, format='jpeg')
        img_str2 = base64.b64encode(buffer.getvalue())

        img_df = pd.DataFrame({'photoID':[photoID], 'data':[img_str], 'analyzedData':[img_str2], 'cameraID':[cameraID]})
        img_df.to_sql('photo', con=engine, if_exists='append', index=False)
    else:
        continue
s.close()