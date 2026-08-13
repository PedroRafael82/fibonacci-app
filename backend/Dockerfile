### Backend multi-stage Dockerfile
# Build stage: use Maven + JDK to build the fat JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace

# copy pom and resolve dependencies (will be cached when pom doesn't change)
COPY pom.xml ./

# copy sources and build
COPY src ./src
RUN mvn -B package -DskipTests

# Runtime stage: lean JRE image
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy the built jar from the build stage
COPY --from=build /workspace/target/backend-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
