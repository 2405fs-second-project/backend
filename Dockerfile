# 백엔드 Dockerfile

# Java 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

WORKDIR /app

# Gradle Wrapper와 필요한 파일들을 복사합니다.
COPY backend/gradlew backend/gradle /app/
COPY backend/build.gradle.kts backend/settings.gradle.kts /app/
COPY backend/src /app/src/

# Gradle 의존성 설치 및 빌드
RUN chmod +x gradlew
RUN ./gradlew build

# 빌드한 JAR 파일을 실행합니다.
COPY backend/build/libs/*.jar app.jar

EXPOSE 8081
CMD ["java", "-jar", "app.jar"]