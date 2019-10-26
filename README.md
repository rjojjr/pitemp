# pitemp
A py4j gateway application to send DHT11/DHT22 readings to PiCenter

PiTemp is now in beta release status.

The main class contains an integer constant PI. PI can be used with
the switch statement to build variants for different Raspberry Pis.

The main class creates a py4j gateway server that passes an Update
object to the PiTemp main.py python service.

The Update constructor constructs a new single client PiCenter
socket connection:

SingleClient(PiCenter IP, PiCenter Hostname(Optional), PiCenter Port);

PiTemp includes mainTM1637, to replace main.py and add TM1637
functionality. 

Requirements:
JVM 8
PiCenter Springboot web application
Raspberry Pi with network access PiCenter server
WiringPI python library(To use TM1637)
Adafruit DHT python library
py4j Python Java Gateway library

You are bound by the license terms contained in this repository
as well as the conditions set forth by the producers of dependencies.