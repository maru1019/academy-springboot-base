# ビルドステージ
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /home/app
COPY . .
RUN gradle clean build -x test

# 実行環境ステージ
FROM eclipse-temurin:17-alpine
WORKDIR /home/app
COPY --from=build /home/app/build/libs/spring-0.0.1-SNAPSHOT.jar /home/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/app.jar"]
