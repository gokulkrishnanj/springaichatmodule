FROM openjdk:17-jdk-alpine
COPY target/springaichatmodel-0.0.1-SNAPSHOT.jar springaichatmodel-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/springaichatmodel-0.0.1-SNAPSHOT.jar"]