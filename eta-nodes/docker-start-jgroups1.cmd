set JAVA_HOME="%JAVA_17_HOME%"

set DOCKER_CONTAINER_ALIAS=eta-cache1

set SERVER_PORT=7701
set CONTAINER_SERVER_PORT=30711
set JGROUPS_RAFT_MEMBERS=A,B,C
set JGROUPS_RAFT_NODE_NAME=A

mvn docker:start -P docker
