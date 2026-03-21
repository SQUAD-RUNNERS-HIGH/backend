FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY ./project.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-dev} -jar app.jar"]
