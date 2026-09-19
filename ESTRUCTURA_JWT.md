# Estructura del Proyecto con JWT

Los archivos añadidos para la funcionalidad de autenticación JWT están ubicados en los siguientes paquetes:

```text
pacientes-spring/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── demo/
│   │   │               ├── controller/
│   │   │               │   └── AuthController.java
│   │   │               ├── dto/
│   │   │               │   ├── AuthRequest.java
│   │   │               │   ├── AuthResponse.java
│   │   │               │   └── RegisterRequest.java
│   │   │               ├── entity/
│   │   │               │   └── User.java
│   │   │               ├── repository/
│   │   │               │   └── UserRepository.java
│   │   │               ├── security/
│   │   │               │   ├── JwtAuthFilter.java
│   │   │               │   ├── JwtUtils.java
│   │   │               │   └── SecurityConfig.java
│   │   │               └── service/
│   │   │                   └── AuthService.java
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── demo/
│                       └── AuthControllerTest.java
```
