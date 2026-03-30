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