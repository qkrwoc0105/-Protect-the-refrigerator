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

filename = 'test.jpg'

try:
    while 1:
        if g.input(KEY):
            print('capture')
            with picamera.PiCamera() as camera:
                camera.resolution=(1024, 768)
                camera.capture(filename)
except KeyboardInterrupt:
    pass;

g.cleanup()