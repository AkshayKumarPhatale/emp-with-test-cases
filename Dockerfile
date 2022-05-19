FROM openjdk:8-jdk-alpine
LABEL maintainer="akshaykumarphatale7@gmail.com"
EXPOSE 3535
RUN mkdir /app
ADD build/libs/*.jar loan-service.jar
ENTRYPOINT ["java","-jar","loan-service.jar"]
