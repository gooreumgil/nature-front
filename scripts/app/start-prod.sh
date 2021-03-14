jar="./app/build/libs/app-0.0.1-SNAPSHOT.jar"
port="8060"

cd ../../
rm ${jar}
./gradlew :core:clean
./gradlew :core:cleanQuerydslSourcesDir
./gradlew :app:bootJar
fuser -k ${port}/tcp
rm nohup.out
java -jar -Dspring.profiles.active=prod ${jar}
