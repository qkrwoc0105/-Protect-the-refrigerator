import RPi.GPIO as g
import picamera
import time

g.setmode(g.BCM)
KEY = 24;
g.setup(KEY, g.IN)

try:
    while 1:
        time.sleep(1)
        if g.input(KEY):
            raspberryPiID = 'applemango'
            cameraID = 'camera1'
            dte = time.localtime()
            Year = dte.tm_year
            Mon = dte.tm_mon
            Day = dte.tm_mday
            WDay = dte.tm_wday
            Hour = dte.tm_hour
            Min = dte.tm_min
            Sec = dte.tm_sec
            photoID = str(Year) + '_' + str(Mon) + '_' + str(Day) + '_' + str(Hour) + '_' + str(Min) + '_' + str(Sec);
                 
            answer = raspberryPiID + cameraID + photoID
            print(answer)
except KeyboardInterrupt:
    pass;

g.cleanup()
