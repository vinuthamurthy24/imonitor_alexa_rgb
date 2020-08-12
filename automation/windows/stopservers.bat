@echo off

echo going to kill tasks
taskkill  /FI "WINDOWTITLE eq IMS_*"

echo going to stop server
net stop tomcat7

rm server.id

echo done