FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/target/user-address-crud.jar .

EXPOSE 8080

CMD ["java", "-jar", "user-address-crud.jar"]