docker pull mashupbranding/mashup-recruit-api:latest
docker pull mashupbranding/mashup-recruit-admin:latest
docker pull mashupbranding/mashup-recruit-batch:latest
docker pull mashupbranding/mashup-recruit-filebeat:latest

/usr/local/bin/docker-compose -f /home/ec2-user/apps/mashup-admin/docker-compose.yml up -d
