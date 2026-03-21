FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY ./build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}", "-jar", "app.jar"]
