# ビルドステージ (例: build)
FROM gradle:7.2-jdk17 AS build
WORKDIR /app
COPY . .

# Renderで設定されているはずの環境変数をここで一時的に確認します
ARG DATABASE_URL
ARG DATABASE_USER
ARG DATABASE_PASSWORD

# 環境変数が設定されているかを表示
RUN echo "DATABASE_URL=$DATABASE_URL" && echo "DATABASE_USER=$DATABASE_USER" && echo "DATABASE_PASSWORD=$DATABASE_PASSWORD"

# テストをスキップしてビルド
RUN gradle build -x test
