set JAVA_HOME="%JAVA_17_HOME%"

mvn clean install jib:build -P jib
