#!/bin/bash

# BLUE가 실행중인지 확인
APP_NAME=mashup-member
EXIST_BLUE=$(docker-compose -p mashup-member-blue -f docker-compose.blue.yml ps | grep Up)

if [ -z "${EXIST_BLUE}" ] # -z는 문자열 길이가 0이면 true.BLUE가 실행중이면 false
then
  # start blue
  START_CONTAINER=blue
  TERMINATE_CONTAINER=green
  START_PORT=8070
  TERMINATE_PORT=8071
else
  # start green
  START_CONTAINER=green
  TERMINATE_CONTAINER=blue
  START_PORT=8071
  TERMINATE_PORT=8070
fi

echo " ========== [start] change ${APP_NAME}-${TERMINATE_CONTAINER} to ${APP_NAME}-${START_CONTAINER} =========="

echo "[step 1] deploy ${APP_NAME}-${START_CONTAINER}"
docker-compose -p ${APP_NAME}-${START_CONTAINER} -f docker-compose.${START_CONTAINER}.yml pull
docker-compose -p ${APP_NAME}-${START_CONTAINER} -f docker-compose.${START_CONTAINER}.yml up -d
for RETRY_CNT in {1..10}
do
  echo "Health Check Start...(${RETRY_CNT})"

  HEALTH_CHECK_RESPONSE=$(curl -s http://127.0.0.1:${START_PORT}/ping | grep 'MashUp')
  if [ -z "${HEALTH_CHECK_RESPONSE}" ] # 실행되었다면 break
  then
        echo "health check fail..${HEALTH_CHECK_RESPONSE}"
  else
        echo "health check success!"
        break
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
          echo "deployment failed."
          exit 1
  fi

  echo "wait 5 seconds..."
  sleep 5
done



echo "deploy ${APP_NAME}-${START_CONTAINER} success!"

# sed 명령어를 이용해서 아까 지정해줬던 service-url.inc의 url값을 변경해줍니다.
# sed -i "s/기존문자열/변경할문자열" 파일경로 입니다.
# 종료되는 포트를 새로 시작되는 포트로 값을 변경해줍니다.
# ex ) sudo sed -i "s/8080/8081/" /nginx/conf.d/app.conf
echo -e "\n[step - 2] change port ${TERMINATE_PORT} to ${START_PORT}"
sed -i "s/${TERMINATE_PORT}/${START_PORT}/" /home/ec2-user/mashup-server/docker/infra/nginx/conf.d/app.conf

# 새로운 포트로 스프링부트가 구동 되고, nginx의 포트를 변경해주었다면, nginx 재시작해줍니다.
# docker exec -it {nginx container name} nginx -s reload
echo -e "\n[step - 3] nginx reload.."
docker exec -i nginx nginx -s reload


# 기존에 실행 중이었던 docker-compose는 종료시켜줍니다.
echo -e "\n[step - 4] exit ${APP_NAME}-${TERMINATE_CONTAINER}"
docker-compose -p ${APP_NAME}-${TERMINATE_CONTAINER} -f docker-compose.${TERMINATE_CONTAINER}.yml down

echo "exit ${APP_NAME}-${TERMINATE_CONTAINER} success!"

echo " ========== [end] change ${APP_NAME}-${TERMINATE_CONTAINER} to ${APP_NAME}-${START_CONTAINER} =========="
