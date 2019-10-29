import Adafruit_DHT

#v1.0.02a
import time
from py4j.java_gateway import JavaGateway, GatewayParameters

PI = 3
#PI 2 PIN 17
#PI1,3-4 PIN 4
DHT_PIN = 4
#Print each reading
DEBUG = 0
#Send every reading to application
DEBUG_SOCKET = 0

def one():
    return JavaGateway()

def two():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25331))

def three():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25332))

def four():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25333))

def oneDHT():
    return JavaGateway()

def twoDHT():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25331))

def threeDHT():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25332))

def fourDHT():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25333))

switcher = {
    1: one,
    2: two,
    3: three,
    4: four
}

def gateway_switch(pinum):
    func = switcher.get(pinum, JavaGateway())
    return func()

gateway = gateway_switch(PI)
app = gateway.entry_point.getApp()
temp = 0
humidity = 0
temperature = 0

def getTemp(t, h):
    #pi 3,4 DHT22
    sensor = Adafruit_DHT.DHT22
    humidity, temperature = Adafruit_DHT.read_retry(sensor, DHT_PIN)
    temp = int((1.8 * temperature) + 32)
    humidity = int(humidity)
    t1 = str(int(temp))
    h1 = str(int(humidity))
    if(DEBUG == 1):
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
    if(DEBUG_SOCKET == 1):
        app.update(str(temp), str(humidity))
    if(DEBUG == 1):
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
