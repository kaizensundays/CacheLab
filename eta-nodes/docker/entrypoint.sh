#!/bin/bash

export FLAGS="-server -Xmx256m"
export FLAGS="$FLAGS -XX:CompileThreshold=10000 -XX:ThreadStackSize=64K -XX:SurvivorRatio=8"
export FLAGS="$FLAGS -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=15"
export FLAGS="$FLAGS -Xshare:off"

export JG_FLAGS="-Djgroups.udp.mcast_addr=232.5.5.5 -Djava.net.preferIPv4Stack=true"

#JMX_PORT=9010
#RMI_PORT=30011

HOST="192.168.0.19"

java $FLAGS $JG_FLAGS \
 -Dlog4j2.configurationFile=log4j2.xml -Dlog4j.shutdownHookEnabled=false -Dlog4j2.debug=false \
 -Dproperties=node.yaml \
 -Dsun.management.jmxremote.level=FINEST \
 -Dsun.management.jmxremote.handlers=java.util.logging.ConsoleHandler \
 -Djava.util.logging.ConsoleHandler.level=FINEST \
 -Dcom.sun.management.jmxremote.local.only=false \
 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.port=$JMX_PORT \
 -Dcom.sun.management.jmxremote.rmi.port=$RMI_PORT \
 -Djava.rmi.server.hostname=192.168.0.19 \
 -Dloader.main=com.kaizensundays.eta.cache.Main \
 -cp service.jar org.springframework.boot.loader.launch.PropertiesLauncher
