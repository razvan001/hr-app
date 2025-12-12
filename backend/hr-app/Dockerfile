
# Runtime stage
FROM amazoncorretto:21.0.4-alpine
WORKDIR /app
COPY build/libs/*.jar hr-app-0.0.1-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app/hr-app-0.0.1-SNAPSHOT.jar"]
