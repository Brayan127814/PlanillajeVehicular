# ====== 1. Build (Maven) ======
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia todo el proyecto
COPY . .

# Construye el .jar
RUN mvn clean package -DskipTests

# ====== 2. Run ======
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia el jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]Y