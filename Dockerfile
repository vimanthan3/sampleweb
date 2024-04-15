FROM openjdk:8
ADD build/libs/sampleapp-2.0.jar test.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","test.jar"]
