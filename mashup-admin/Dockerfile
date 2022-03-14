FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /logs
COPY scouter /scouter
ARG SCOUTER_OBJECT_NAME
ARG SCOUTER_HOST
ARG JAR_FILE
COPY ${JAR_FILE:-build/libs/mashup-admin.jar} app.jar
ENTRYPOINT exec java ${JAVA_OPTS} \
-javaagent:/scouter/agent.java/scouter.agent.jar \
-Dobj_name=${SCOUTER_OBJECT_NAME} \
-Dnet_collector_ip=${SCOUTER_HOST} \
-jar /app.jar
EXPOSE 8090