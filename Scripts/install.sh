#!/bin/bash

sudo apt-get update
#Install pip
sudo apt install python-pip
#Install openJDK8
sudo apt-get install -y galternatives openjdk-8-jdk
#Install wiringPi
sudo pip install wiringPi
#Install Adafruit DHT library
sudo pip install Adafruit_DHT
#Install py4j
sudo pip install py4j
#Put main.py, pitemp-1.0.01a.jar, startJ.sh and startPy.sh in home dir.
sudo chmod +x /home/pi/startJ.sh
sudo chmod +x /home/pi/startPy.sh
