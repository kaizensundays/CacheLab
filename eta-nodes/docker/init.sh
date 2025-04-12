#!/bin/sh

cd /opt

dos2unix *.sh
dos2unix *.xml
dos2unix *.yaml

chmod ug+x *.sh

apk update; \
apk add vim; \
apk add bash; \
apk add htop; \
apk add unzip;
