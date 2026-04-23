<<<<<<< HEAD
# 1. Imagen base: Usamos Java 21 (Temurin es excelente y ligera)
FROM eclipse-temurin:21-jdk 

# 2. Directorio de trabajo: Creamos una carpeta 'app' dentro del contenedor
WORKDIR /app

# 3. Copiar el archivo: Traemos el .jar generado por Maven desde tu PC al contenedor
# Asegúrate de que el nombre del archivo en 'target' coincida exactamente
COPY target/planillaje-vehicular-0.0.1-SNAPSHOT.jar app.jar

# 4. Punto de entrada: El comando que mantiene vivo al contenedor ejecutando tu app
ENTRYPOINT ["java", "-jar", "app.jar"]


#FROM, WORKDIR, COPY
=======
# ====== 1. Build (Maven) ======
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# ====== 2. Run ======
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
