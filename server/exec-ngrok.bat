@echo off
echo BOOT HOOP PROJECT by NGROK
start /B ngrok http --domain=surely-quality-robin.ngrok-free.app 8080
java -jar hoop-0.0.1-SNAPSHOT.jar --spring.profiles.active=ngrok

