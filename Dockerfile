# Etapa 1: Construcción (Maven con Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# Compilamos saltando los tests para acelerar el despliegue en Render
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime con Java 21)
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
# Copiamos el jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render asigna un puerto dinámico mediante la variable PORT
# Si no existe, usamos el 8080 por defecto (estándar de Spring)
ENV PORT=8080
EXPOSE ${PORT}

# Ejecutamos la app pasando el puerto como argumento de Spring
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]