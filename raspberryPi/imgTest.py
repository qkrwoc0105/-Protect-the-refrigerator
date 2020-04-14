import pandas as pd
import pymysql
from sqlalchemy import create_engine
from PIL import Image
import base64
from io import BytesIO

engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.102.154/pytest', echo = False)

img_df = pd.read_sql(sql="select * from images", con=engine)
img_str = img_df['data'].values[0]
print(type(img_str))

img = base64.decodebytes(img_str)
im = Image.open(BytesIO(img))
im.save('py.png')
