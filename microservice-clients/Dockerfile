# --- 1. Etapa de Construcción (Build Stage) ---
# Imagen de Maven que usa una distribución de OpenJDK 21.
FROM maven:3.9-eclipse-temurin-21 AS build

# Directorio de trabajo
WORKDIR /app

# Copiamos el pom.xml para cachear las dependencias.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente.
COPY src ./src

# Compilamos el proyecto y generamos el JAR.
RUN mvn package -DskipTests


# --- 2. Etapa de Ejecución (Run Stage) ---
# Imagen ligera que solo contiene el JRE de OpenJDK 21.
FROM openjdk:21-slim

WORKDIR /app

# Copiamos el JAR desde la etapa de construcción.
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto de la aplicación.
EXPOSE 8080

# Comando para arrancar la aplicación.
ENTRYPOINT ["java", "-jar", "app.jar"]