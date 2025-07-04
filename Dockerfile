FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /app/target/zucktest-1.0.0.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]