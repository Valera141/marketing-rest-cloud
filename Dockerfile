# 1. Usamos una imagen que ya tiene Maven y Java 21 instalados
FROM maven:3.9.9-eclipse-temurin-21-alpine

# 2. Carpeta de trabajo
WORKDIR /app

# 3. Copiamos los archivos
COPY . .

# 4. Compilamos usando el comando 'mvn' directo (no el wrapper ./mvnw)
RUN mvn clean package -DskipTests

# 5. Exponemos el puerto
EXPOSE 8080

# 6. Ejecutamos el JAR generado
ENTRYPOINT ["java", "-jar", "target/Marketing-0.0.1-SNAPSHOT.jar"]
