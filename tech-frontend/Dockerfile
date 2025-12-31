FROM openjdk:8-jdk-alpine as builder

ADD ./tech-frontend/target/tech-frontend-*.jar /app.jar

RUN sh -c 'touch /app.jar'

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]