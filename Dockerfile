FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar /app/nagnae.jar
ENTRYPOINT ["java", "-jar", "/app/nagnae.jar"]
EXPOSE 8080