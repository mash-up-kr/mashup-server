FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE:-build/libs/mashup-api.jar} app.jar
RUN mkdir -p /logs
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080