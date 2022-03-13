# CodeDeploy agent 설치용 스크립트
sudo yum update -y
sudo yum install ruby -y
sudo yum install wget -y

wget https://aws-codedeploy-ap-northeast-2.s3.ap-northeast-2.amazonaws.com/latest/install

sudo chmod +x ./install
sudo ./install auto
