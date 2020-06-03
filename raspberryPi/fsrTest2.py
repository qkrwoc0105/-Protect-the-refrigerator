import RPi.GPIO as GPIO
import time
import picamera

GPIO.setmode(GPIO.BCM)
KEY = 4
GPIO.setup(KEY,GPIO.IN)

try:
    while True:
        input = GPIO.input(KEY)
        print(input)
        time.sleep(1)
except KeyboardInterrupt:
    pass
finally:
    GPIO.cleanup()

