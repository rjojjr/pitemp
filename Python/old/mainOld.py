#Updates TM1637 with each db update.

import RPi.GPIO as GPIO
import tm1637
import Adafruit_DHT

tm = tm1637.TM1637(clk=23, dio=24)

# v1.0.00a
import time
from py4j.java_gateway import JavaGateway, GatewayParameters

gateway = JavaGateway()
#gateway = JavaGateway(gateway_parameters=GatewayParameters(port=25333))
app = gateway.entry_point.getApp()
temp = 0
humidity = 0
temperature = 0


def getTemp(t, h):
    sensor = Adafruit_DHT.DHT11
    pin = 4
    humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
    temp = int((1.8 * temperature) + 32)
    humidity = int(humidity)
    t1 = str(int(temp))
    h1 = str(int(humidity))
    tm.numbers(int(t1[0] + t1[1]), int(h1[0] + h1[1]))
    print "Temp: " + str(temp) + " Humidity: " + str(humidity)
    t.append(temp)
    h.append(humidity)


def getTempE():
    sensor = Adafruit_DHT.DHT11
    pin = 4
    humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
    temp = int((1.8 * temperature) + 32)
    humidity = int(humidity)
    t = str(int(temp))
    h = str(int(humidity))
    tm.numbers(int(t[0] + t[1]), int(h[0] + h[1]))
    print "Temp: " + str(temp) + " Humidity: " + str(humidity)


def getAvg(tr, hr):
    t = 0
    h = 0
    for x in tr:
        t += x
    for y in hr:
        h += y
    td = t / 18.0
    th = h / 18.0
    t = int(td)
    h = int(th)
    #t1 = str((1.8 * temperature) + 32)
    #h1 = str(humidity)
    #tm.numbers(int(t1[0] + t1[1]), int(h1[0] + h1[1]))
    print "Update @ Temp: " + str(t) + " Humidity: " + str(h)
    app.update(str(t), str(h))


while True:
    treadings = [0]
    hreadings = [0]
    # first = True
    print "Reading..."
    for x in range(24):
        if x >= 6:
            getTemp(treadings, hreadings)
        if x < 6:
            getTempE()
        time.sleep(4)
    getAvg(treadings, hreadings)
    time.sleep(30)
