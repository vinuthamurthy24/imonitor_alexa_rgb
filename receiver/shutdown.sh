#!/bin/bash
java -jar -Djavax.net.ssl.trustStore=mySrvKeystore  -Djavax.net.ssl.trustStorePassword=123456 receiver.jar stop &
