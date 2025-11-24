# Plataforma de Registro de Voluntariado

Backend desarrollado en Spring Boot para gestionar actividades de voluntariado, incluyendo autenticación, roles, voluntarios, actividades, asignaciones y reportes.

## Requisitos Previos

- **Java 17** o superior instalado.
- **Maven** para construir el proyecto.
- (Opcional) MongoDB si no usas el perfil embebido.

## Construcción del Proyecto

1. Clona o descarga el proyecto.
2. Navega al directorio raíz del proyecto.
3. Ejecuta el comando para construir el JAR:

   ```
   mvn clean package
   ```

   Esto generará el archivo `target/plataforma-registro-voluntariado-0.0.1-SNAPSHOT.jar`.

## Ejecución del Backend

Para encender el servidor:

1. Abre una terminal en el directorio del proyecto.
2. Ejecuta el siguiente comando:

   ```
   $env:JWT_SECRET = "egrf9CK5k2Iu+392cO3LobrE5obOH7vNogVZubUeC/0="; java "-Dspring.profiles.active=test" -jar target/plataforma-registro-voluntariado-0.0.1-SNAPSHOT.jar
   ```

3. Espera a que aparezca el mensaje: "Tomcat started on port 8080".
4. El servidor estará disponible en `http://localhost:8080`.

### Notas
- El perfil "test" usa MongoDB embebido, pero puede mostrar errores de conexión (el servidor arranca igual).
- Para detener el servidor, presiona Ctrl + C en la terminal.

## Pruebas con Postman

Usa los endpoints documentados en `ENDPOINTS.md`. Ejemplos:

- **GET** `http://localhost:8080/actuator/health` (verifica el estado).
- **POST** `http://localhost:8080/api/auth/login` con body:
  ```json
  {
    "username": "admin",
    "password": "Admin123*"
  }
  ```

## Documentación de API

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Endpoints detallados en `ENDPOINTS.md`.

## Tecnologías Usadas

- Spring Boot 3.3.4
- MongoDB
- JWT para autenticación
- Maven para gestión de dependencias