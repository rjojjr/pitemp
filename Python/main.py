import Adafruit_DHT

#v1.0.02a
import time
from py4j.java_gateway import JavaGateway, GatewayParameters

pi = 1

def one():
    return JavaGateway()

def two():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25331))

def three():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25332))

def four():
    return JavaGateway(gateway_parameters=GatewayParameters(port=25333))

switcher{
    1: one,
    2: two,
    3: three,
    4: four
}

def gateway_switch(pi):
    func = switcher(pi, JavaGateway())
    return func()

gateway = gateway_switch(pi)
app = gateway.entry_point.getApp()
temp = 0
humidity = 0
temperature = 0




def getTemp(t, h):
    sensor = Adafruit_DHT.DHT11
    pin = 17
    humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
    temp = int((1.8 * temperature) + 32)
    humidity = int(humidity)
    print "Temp: " + str(temp) + " Humidity: " + str(humidity)
    t.append(temp)
    h.append(humidity)

def getTempE():
    sensor = Adafruit_DHT.DHT11
    pin = 17
    humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)
    temp = int((1.8 * temperature) + 32)
    humidity = int(humidity)
    print "Temp: " + str(temp) + " Humidity: " + str(humidity)

def getAvg(tr, hr):
    t = 0
    h = 0
    for x in tr:
        t+= x
    for y in hr:
        h+= y
    td = t/18.0
    th = h/18.0
    t = int(td)
    h = int(th)
    print "Update @ Temp: " + str(t) + " Humidity: " + str(h)
    app.update(str(t), str(h))

while True:
    treadings = [0]
    hreadings = [0]
    #first = True
    print "Reading..."
    for x in range(24):
        if x >=6:
            getTemp(treadings, hreadings)
        if x <6:
            getTempE()
        time.sleep(4)
    getAvg(treadings, hreadings)
    time.sleep(30)