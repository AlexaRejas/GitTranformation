FROM amazoncorretto:17-alpine-jdk

# Copiar el jar a la carpeta /app con el nombre correcto
COPY target/tranformacion-0.0.1-SNAPSHOT.jar /app/tranformacion-0.0.1-SNAPSHOT.jar

# Definir el entrypoint apuntando al jar correcto
ENTRYPOINT ["java", "-jar", "/app/tranformacion-0.0.1-SNAPSHOT.jar"]
