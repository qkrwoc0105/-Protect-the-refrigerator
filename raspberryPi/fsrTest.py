import RPi.GPIO as GPIO
import time
import picamera

GPIO.setmode(GPIO.BCM)
KEY = 4
GPIO.setup(KEY,GPIO.IN)

filename = 'test.jpg'
pre = GPIO.input(KEY)
try:
    while True:
        now = GPIO.input(KEY)
        print(pre, now)
        if pre == 1 and now == 0:
            with picamera.PiCamera() as camera:
                camera.vflip = True
                camera.hflip = True
                camera.capture(filename)
        time.sleep(1)
        pre = now
except KeyboardInterrupt:
    pass
finally:
    GPIO.cleanup()
