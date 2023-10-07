jar="./app/build/libs/app-0.0.1-SNAPSHOT.jar"
port="8090"

cd ../../
rm ${jar}
fuser -k ${port}/tcp

chmod +x ./gradlew
./gradlew :core:clean
./gradlew :core:cleanQuerydslSourcesDir
./gradlew :app:bootJar -x asciidoctor
rm nohup.out
java -jar -Dspring.profiles.active=dev ${jar}
