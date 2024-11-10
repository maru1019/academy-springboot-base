# ビルドステージ (例: build)
FROM gradle:7.2-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build

# 最終ステージ (実行環境)
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/my-app.jar /app/my-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/my-app.jar"]
