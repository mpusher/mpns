#!/bin/sh

ENV=dev

base_dir=`pwd`

echo "start install mpns..."
mvn clean install -Dmaven.test.skip=true -P $ENV

cd mpns-web

MAVEN_OPTS="-Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

export MAVEN_OPTS

mvn -Djetty.http.port=9099 jetty:run
