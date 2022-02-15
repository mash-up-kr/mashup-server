FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /logs
ARG JAR_FILE
COPY ${JAR_FILE:-build/libs/mashup-batch.jar} app.jar
ENTRYPOINT exec java ${JAVA_OPTS} -jar /app.jars