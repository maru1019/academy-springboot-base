FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY src/main/resources/application-docker.properties ./application-docker.properties
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.additional-location=classpath:/,file:./application-docker.properties"]