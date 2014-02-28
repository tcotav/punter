#!/bin/bash

# yeah -- this is terrible.  its my hack while developing...
TOMCAT=/Users/james/Applications/tomcat
${TOMCAT}/bin/shutdown.sh
rm -Rf ${TOMCAT}/webapps/punter*
cp /Users/james/dev/chef/comparison/java/punt/out/artifacts/punter.war ${TOMCAT}/webapps/
${TOMCAT}/bin/startup.sh
