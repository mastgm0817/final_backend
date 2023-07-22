FROM adoptopenjdk:11-jdk-hotspot
EXPOSE 8082
ADD build/libs/final_backend.jar final_backend.jar
ENTRYPOINT ["java","-jar","final_backend.jar"]