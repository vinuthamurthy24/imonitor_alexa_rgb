@echo off

set SRV_ID=%RANDOM%

if exist server.id echo "server already running"
if exist server.id goto end

echo starting the server
net start tomcat7
if errorlevel 1 goto error

echo %SRV_ID% >server.id


echo starting balancer
cd balancer
start "ims_balancer" java -jar -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 -Dnamekey=%SRV_ID% balancer.jar 

echo starting receiver
cd ..\receiver
start "ims_receiver" java -jar -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 -Dnamekey=%SRV_ID% receiver.jar 

echo starting broadcaster
cd ..\broadcaster
start "ims_broadcaster" java -jar -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 -Dnamekey=%SRV_ID% broadcaster.jar

echo starting imonitorclient
cd ..\imonitorclient
start "ims_imonitorclient" java -jar imonitorclient.jar


cd .. 
echo done
goto end

:error
echo error starting server

:end
