FROM adoptopenjdk/openjdk11
ENV TZ=Asia/Seoul
ENV JAVA_OPTS="-Djava.awt.headless=true"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /logs

apk add --no-cache freetype ttf-dejavu fontconfig
#COPY scouter /scouter
#ARG SCOUTER_OBJECT_NAME
#ARG SCOUTER_HOST
ARG JAR_FILE=/mashup-admin/build/libs/mashup-admin.jar app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java ${JAVA_OPTS} \
#-javaagent:/scouter/agent.java/scouter.agent.jar \
#-Dobj_name=${SCOUTER_OBJECT_NAME} \
#-Dnet_collector_ip=${SCOUTER_HOST} \
-jar /app.jar
EXPOSE 8090

