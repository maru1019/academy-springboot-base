# ベースイメージの指定（1行のみでOK）
FROM eclipse-temurin:17-jdk-alpine

# 作業ディレクトリの設定
WORKDIR /app

# ビルドしたJARファイルをコンテナにコピー
COPY build/libs/*.jar app.jar

# ポート指定（8080）
EXPOSE 8080

# アプリケーションの実行
ENTRYPOINT ["java", "-jar", "app.jar"]git