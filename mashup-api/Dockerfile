FROM openjdk:8-jdk-alpine
COPY ${JAR_FILE:-build/libs/mashup-api.jar} app.jar
RUN mkdir -p /logs
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080