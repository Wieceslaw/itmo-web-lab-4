FROM amazoncorretto:17-alpine-jre
ARG JAR_FILE=target/Lab4-0.0.1-SNAPSHOT.jar
WORKDIR /opt/itmo/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]