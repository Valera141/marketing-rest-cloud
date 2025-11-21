# 1. Usar una imagen base de Java 21 (Ligera)
FROM eclipse-temurin:21-jdk-alpine

# 2. Crear carpeta de trabajo
WORKDIR /app

# 3. Copiar todos los archivos de tu proyecto al contenedor
COPY . .

# 4. Dar permisos de ejecución al instalador de Maven
RUN chmod +x mvnw

# 5. Compilar el proyecto (saltando los tests para que sea rápido)
RUN ./mvnw clean package -DskipTests

# 6. Exponer el puerto 8080 (donde vive Spring Boot)
EXPOSE 8080

# 7. El comando para iniciar la app
ENTRYPOINT ["java", "-jar", "target/Marketing-0.0.1-SNAPSHOT.jar"]
