FROM openjdk:8-jdk-alpine
VOLUME /tmp

ENV PORT=9999
ENV CommandParamters="-Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT"

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE ${PORT}
ENTRYPOINT ["java","$CommandParamters","-jar","/app.jar"]