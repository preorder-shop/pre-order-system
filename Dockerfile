FROM gradle:8.5-jdk AS build
WORKDIR /app

# build.gradle 과 src/ 디렉토리 복사
COPY build.gradle .
COPY src ./src

# 애플리케이션 빌드
RUN ./gradlew clean build

# 두번째 단계 : 실행 환경 설정
FROM openjdk:17
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일 복사
COPY --from=build /app/target/*.jar ./app.jar

# 애플리케이션 실행
EXPOSE 8080
CMD ["java","-jar","app.jar"]