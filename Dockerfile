# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY build/libs/*.jar app.jar
# COPY src/main/resources/application-docker.properties ./application-docker.properties
# ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.additional-location=classpath:/,file:./application-docker.properties"]



# 一時的にJARをビルドするステージ
# FROM openjdk:17-jdk-slim AS build
# WORKDIR /app

# # Gradleをインストール
# RUN apt-get update && apt-get install -y curl unzip zip
# RUN curl -s https://get.sdkman.io | bash
# RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle 8.7"

# # プロジェクトの全ファイルをコピー
# COPY . /app

# # JARをビルド
# RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && gradle clean build -x test"

# # 最終ステージ（実行用）
# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY --from=build /app/build/libs/*.jar app.jar
# COPY src/main/resources/application-docker.properties ./application-docker.properties

# ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.additional-location=classpath:/,file:./application-docker.properties"]




FROM openjdk:17-jdk-slim

WORKDIR /app
COPY build/libs/*.jar app.jar
COPY src/main/resources/application-postgresql.properties ./application-postgresql.properties

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.additional-location=classpath:/,file:./application-postgresql.properties"]
