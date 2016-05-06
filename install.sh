#!/bin/sh

ENV=daily

base_dir=`pwd`

echo "start install mpns..."
mvn clean install -Dmaven.test.skip=true -P $ENV

cp mpns-web/target/mpns.war /opt/shinemo/apache-tomcat-7.0.62/webapps
