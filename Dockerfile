FROM openjdk:17-jdk-alpine
MAINTAINER danielbogosel
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]