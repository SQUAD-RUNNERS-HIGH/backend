FROM gradle:8.11.1-jdk17
WORKDIR /app
COPY . /app
CMD ["gradle", "bootRun"]