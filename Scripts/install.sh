#!/bin/bash

#sudo apt-get update
apt-get update
#Install pip
#sudo apt install python-pip
apt install python-pip
#Install openJDK8
#sudo apt-get install -y galternatives openjdk-8-jdk
apt-get install -y galternatives openjdk-8-jdk
#Install wiringPi
#sudo pip install wiringPi
pip install wiringPi
#Install Adafruit DHT library
#sudo pip install Adafruit_DHT
pip install Adafruit_DHT
#Install py4j
#sudo pip install py4j
pip install py4j
#Put main.py, pitemp-1.0.01a.jar, startJ.sh and startPy.sh in home dir.
#sudo chmod +x /home/pi/startJ.sh
chmod +x /home/pi/startJ.sh
#sudo chmod +x /home/pi/startPy.sh
chmod +x /home/pi/startPy.sh
