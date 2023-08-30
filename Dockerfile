FROM adoptopenjdk:11-jdk-hotspot

# 환경변수 추가
ENV GOOGLE_APPLICATION_CREDENTIALS=./service-account.json

EXPOSE 8082
COPY service-account.json ./service-account.json
ADD build/libs/final_backend.jar final_backend.jar
ENTRYPOINT ["java","-jar","final_backend.jar"]
