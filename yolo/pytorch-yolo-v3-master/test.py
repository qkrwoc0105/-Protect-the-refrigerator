import numpy as np
from PIL import ImageFont, ImageDraw, Image
import cv2
import time

## Make canvas and set the color
img = np.zeros((200,400,3),np.uint8)
b,g,r,a = 0,255,0,0

## Use cv2.FONT_HERSHEY_XXX to write English.
text = time.strftime("%Y/%m/%d %H:%M:%S", time.localtime()) 
cv2.putText(img,  text, (50,50), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (b,g,r), 1, cv2.LINE_AA)

## Use simsum.ttc to write Chinese.
fontpath = "fonts/한수원_한울림_R.ttf"     
font = ImageFont.truetype(fontpath, 32)
img_pil = Image.fromarray(img)
draw = ImageDraw.Draw(img_pil)
draw.text((50, 100),  "아라따!", font = font, fill = (b, g, r, a))
img = np.array(img_pil)

## Display 
cv2.imshow("res", img);cv2.waitKey();cv2.destroyAllWindows()

        #engine = create_engine('mysql+pymysql://hello:Csedbadmin!1@13.125.102.154/pytest', echo = False)        
        #food_df = pd.read_sql(sql="select * from photographedFood", con=engine)
        
        #if len(food_df) == 0:

        #else :
        #    pre_food_df = food_df.values[len(food_df) - 1]
            
        #food_df = pd.DataFrame({'photoID':[], 'foodName':[], 'shelfLife':[], 'inday':[now], 'outday':[], 'x1':[], 'x2':[], 'y1':[], 'y2':[]})
        #food_df.to_sql('photographedFood', con=engine, if_exists='append', index=False)