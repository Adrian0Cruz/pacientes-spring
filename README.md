# Patient Management API

API REST independiente para la gestión de pacientes, construida como práctica de fundamentos backend. 

## Tecnologías
- Java 21
- Spring Boot 3
- Spring Data JPA & Validation
- PostgreSQL
- Docker & Docker Compose

## Cómo ejecutar el proyecto

Asegúrate de tener [Docker](https://www.docker.com/) instalado y corriendo en tu máquina.

1. Clona este repositorio o abre la carpeta en tu terminal.
2. Ejecuta el siguiente comando para construir y levantar los contenedores:
```bash
docker compose up --build
```

**Nota sobre Seguridad (JWT)**:
El proyecto utiliza JWT para proteger los endpoints de pacientes. Debes registrar un usuario e iniciar sesión para obtener el token. Opcionalmente, puedes configurar tu propia clave secreta mediante la variable de entorno `JWT_SECRET`.

### Rutas Públicas
- `POST /auth/register`: Envía JSON con `name`, `email`, `password`.
- `POST /auth/login`: Envía JSON con `email`, `password`. Retorna el Token JWT.

### Rutas Protegidas
- Para acceder a `GET /auth/me` o cualquier endpoint bajo `/patients`, necesitas enviar el header:
`Authorization: Bearer <TU_TOKEN>`