FROM openjdk:17

COPY app.jar app.jar

ENTRYPOINT exec java -jar app.jar
