FROM openjdk:8-jdk-alpine
# FROM oracle/graalvm-ce:20.1.0-java8

WORKDIR /opt/poc-contacts

ARG JAR_FILE=target/poc-contacts-1.0.0.jar

# cp poc-contacts-1.0.0.jar /opt/poc-contacts/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/poc-contacts/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
