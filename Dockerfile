# ビルドステージ
FROM openjdk:17-jdk-slim AS build
WORKDIR /build
COPY . .
RUN ./gradlew clean build -x test

# 実行ステージ
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /build/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
