if [ -d /home/ec2-user/apps/mashup-admin/build ]; then
  rm -rf /home/ec2-user/apps/mashup-admin/build
fi

mkdir -vp /home/ec2-user/apps/mashup-admin/build

/usr/local/bin/docker-compose -f /home/ec2-user/apps/mashup-admin/docker-compose.yml down

if [[ "$(docker ps -a -q 2> /dev/null)" != "" ]]; then
    docker stop $(docker ps -a -q)
    docker rm $(docker ps -a -q)
fi

if [[ "$(docker images -q 2> /dev/null)" != "" ]]; then
    docker rmi $(docker images -q)
fi