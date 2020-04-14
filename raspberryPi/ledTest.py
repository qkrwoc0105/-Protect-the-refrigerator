import RPi.GPIO as g
import time

g.setmode(g.BOARD)
LED = 11
g.setup(LED, g.OUT, initial=g.LOW)

try:
    while 1:
        g.output(LED, g.HIGH)
        time.sleep(1)
        g.output(LED, g.LOW)
        time.sleep(1)
except KeyboardInterrupt:
    pass;
g.cleanup()