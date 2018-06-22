ENV PORT=9999
ENV CommandParamters="-Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT"

FROM openjdk:8-jdk-alpine
VOLUME /tmp

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","$CommandParamters","-jar","/app.jar"]