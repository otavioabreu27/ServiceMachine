FROM openjdk:22-jre-slim

WORKDIR /app

COPY target/ServiceMachine-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]