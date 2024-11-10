# ベースイメージの指定
FROM eclipse-temurin:17

# 作業ディレクトリの設定
WORKDIR /app

# Gradleのインストール
COPY gradle /app/gradle
COPY gradlew /app/
COPY build.gradle /app/
COPY settings.gradle /app/

# 必要な依存関係をインストールしてビルド
RUN ./gradlew build --no-daemon

# ビルドしたJARファイルをコンテナにコピー
COPY build/libs/*.jar app.jar

# ポート指定（8080）
EXPOSE 8080

# アプリケーションの実行
ENTRYPOINT ["java", "-jar", "app.jar"]
