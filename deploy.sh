REPOSITORY=~/app
PROJECT_NAME=프로젝트이름

cd $REPOSITORY/$PROJECT_NAME/

echo "> git pull"

git pull

echo "> 프로젝트 Build 시작"

./gradlew build

echo "> 디렉토리 이동"

cd $REPOSITORY

echo "> build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.war $REPOSITORY

echo "> 구동중인 어플리케이션 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME})

echo "> 구동중인 어플리케이션 : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 어플리케이션이 없으므로 종료하지 않습니다"
else
        echo "> 구동중인 어플리케이션을 종료합니다"
        kill -9 $CURRENT_PID
fi

echo "> 어플리케이션 배포"

WAR_NAME=$(ls -tr $REPOSITORY/|grep *.war | tail -n 1)

echo "> WAR 이름 : $WAR_NAME"

nohup java -jar $REPOSITORY/$WAR_NAME 2>&1 &