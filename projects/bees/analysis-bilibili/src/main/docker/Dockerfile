FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENV CommandParamters="-Djava.security.egd=file:/dev/./urandom"

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","$CommandParamters","-jar","/app.jar"]