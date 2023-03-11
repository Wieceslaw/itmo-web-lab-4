FROM amazoncorretto:17-alpine-jdk
MAINTAINER se.ifmo.ru
COPY target/Lab4-0.0.1-SNAPSHOT.jar Lab4-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/Lab4-0.0.1-SNAPSHOT.jar"]