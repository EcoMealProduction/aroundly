FROM openjdk:21-jdk-slim
LABEL authors="ecomeal-production"
COPY infra/target/infra-0.0.1.jar app/app.jar

ENTRYPOINT ["java", "-jar", "app/app.jar"]