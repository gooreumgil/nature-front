jar="./app/build/libs/app-0.0.1-SNAPSHOT.jar"
port="8090"

cd ../../
rm ${jar}

chmod +x ./gradlew
./gradlew :core:clean
./gradlew :core:cleanQuerydslSourcesDir
./gradlew :app:bootJar -x test
fuser -k ${port}/tcp
rm nohup.out
java -jar -Dspring.profiles.active=dev ${jar}
