FROM eclipse-temurin:25-jdk-jammy AS builder
WORKDIR /workspace
COPY . .
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:25-jre-jammy
WORKDIR /app

# Copia apenas o JAR do estágio de build
COPY --from=builder /workspace/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]