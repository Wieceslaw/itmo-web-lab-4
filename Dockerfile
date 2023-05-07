FROM openjdk:17

COPY target/Lab4-0.0.1-SNAPSHOT.jar /

ENTRYPOINT exec java -jar /Lab4-0.0.1-SNAPSHOT.jar
