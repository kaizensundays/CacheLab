set JAVA_HOME="%JAVA_17_HOME%"

set DOCKER_CONTAINER_ALIAS==eta-cache2

set SERVER_PORT=7701
set CONTAINER_SERVER_PORT=30721
set JGROUPS_RAFT_MEMBERS=A,B,C
set JGROUPS_RAFT_NODE_NAME=B

mvn docker:start -P docker
