FROM openjdk:8-jre-alpine

ADD /target/*.jar /app.jar

EXPOSE 8098 5701/udp

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]