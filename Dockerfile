FROM adoptopenjdk:11-jdk-hotspot

# 환경변수 추가
ENV GOOGLE_APPLICATION_CREDENTIALS=luvoostgatest-05d711662132.json

EXPOSE 8082
COPY luvoostgatest-05d711662132.json ./luvoostgatest-05d711662132.json
ADD build/libs/final_backend.jar final_backend.jar
ENTRYPOINT ["java","-jar","final_backend.jar"]
