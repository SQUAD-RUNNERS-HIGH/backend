FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY ./project.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
