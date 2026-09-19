# Explicación de Capas para JWT y Seguridad

En esta actualización agregamos el ecosistema de seguridad de Spring Security basado en Tokens JWT. 

## 1. `entity/User.java` y `repository/UserRepository.java`
**¿Qué hacen?** Manejan la información de los usuarios que pueden iniciar sesión.
- `User`: Representa al usuario, su email y su contraseña (la cual guardamos siempre encriptada con BCrypt).
- `UserRepository`: Busca usuarios por correo en la base de datos para verificar que existan al hacer login o evitar duplicados en el registro.

## 2. `dto/` (Data Transfer Objects)
**¿Qué hacen?** Son "sobres de correo" ligeros.
En vez de pasar la entidad entera, usamos `RegisterRequest`, `AuthRequest` y `AuthResponse` para estructurar la información exacta que recibimos (email y contraseña) y enviamos (el Token) en nuestros endpoints.

## 3. `security/` (La bóveda y los guardias)
Esta es la capa encargada de la protección.
- **`SecurityConfig.java`**: Es el jefe de seguridad. Él decide qué endpoints son públicos (como `/auth/login`) y cuáles requieren credenciales (`/patients`).
- **`JwtUtils.java`**: Es la máquina de sellado. Fabrica los tokens JWT firmándolos con nuestra clave secreta y verifica que los tokens que recibimos sigan vigentes (menos de 24 horas).
- **`JwtAuthFilter.java`**: Es el guardia en la puerta. Antes de que cualquier petición llegue a nuestros controladores, este filtro detiene la petición, revisa si trae la cabecera `Authorization: Bearer <token>`, saca el token, lo valida usando `JwtUtils` y si todo es correcto, le permite pasar.

## 4. `service/AuthService.java` y `controller/AuthController.java`
**¿Qué hacen?** Contienen la lógica de negocio para el acceso.
- **AuthService**: Se encarga de verificar que el email no esté tomado al registrar y de comparar las contraseñas al hacer login usando `BCryptPasswordEncoder`.
- **AuthController**: Expone las rutas `/auth/login`, `/auth/register` y `/auth/me` para que los usuarios (o nuestro frontend) puedan interactuar con el sistema de autenticación.
