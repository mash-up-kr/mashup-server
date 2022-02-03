FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/mashup-api.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /logs
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080