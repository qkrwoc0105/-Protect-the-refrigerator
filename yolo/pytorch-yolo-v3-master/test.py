import pandas as pd
import pymysql
from sqlalchemy import create_engine
from PIL import Image
import base64
from io import BytesIO

from glob import glob
import subprocess
import sys

conn = pymysql.connect(host='13.125.151.180', user='hello', password='Csedbadmin!1', db='pytest')
curs = conn.cursor()

engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.151.180/pytest', echo = False)

raspberryPiID = 'applemango'
cameraID = 'camera1'
photoID = '2020_6_15_5_50_3'
recvData = raspberryPiID + ' ' + cameraID + ' ' + photoID
dataSplit = recvData.split()

raspberryPiID = dataSplit[0]
cameraID = dataSplit[1]
photoID = dataSplit[2]

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

sys.exit()
conn.close()