# Explicación de Capas para JWT y Seguridad

En esta actualización agregamos el ecosistema de seguridad de Spring Security basado en Tokens JWT. 

## 1. `entity/User.java` y `repository/UserRepository.java`
**¿Qué hacen?** Manejan la información de los usuarios que pueden iniciar sesión.
- `User`: Representa al usuario, su email y su contraseña (la cual guardamos siempre encriptada con BCrypt).
- `UserRepository`: Busca usuarios por correo en la base de datos para verificar que existan al hacer login o evitar duplicados en el registro.

## 2. `dto/` (Data Transfer Objects u Objetos de Transferencia de Datos)

### ¿Qué son y para qué sirven?
Imagina que vas a entrar a un edificio seguro. Para identificarte, solo muestras tu credencial con tu nombre y foto; no llevas tu historial médico completo, tus escrituras de propiedad ni tu acta de nacimiento.

Un **DTO (Data Transfer Object)** es exactamente eso: una clase simple que sirve como un **"paquete o formulario a la medida"** para enviar y recibir datos entre el cliente (Postman, app web, móvil) y el servidor, **sin exponer ni mezclar la entidad de la base de datos**.

### ¿Por qué no usamos directamente la entidad `User`?
1. **Seguridad (Peligro de fuga de datos):** La entidad `User` tiene el campo `password`. Si retornamos directamente la entidad en una petición, correríamos el riesgo de enviar la contraseña (incluso encriptada) al cliente.
2. **Entrada exacta de datos:** Para iniciar sesión solo necesitamos `email` y `password`. La entidad `User` exige `id`, `name`, `createdAt`, etc. Sería incorrecto e incómodo obligar al cliente a enviar campos innecesarios.
3. **Desacoplamiento:** Si el día de mañana cambiamos la tabla en PostgreSQL (por ejemplo, renombrando columnas), los contratos externos (nuestros JSON) no se rompen porque los DTOs se mantienen iguales.

### ¿Cuáles DTOs tenemos en este proyecto y qué hace cada uno?
- **`RegisterRequest`**: Es el formulario de registro. Contiene solo lo necesario para dar de alta a alguien: `name`, `email` y `password`.
- **`AuthRequest`**: Es el formulario de inicio de sesión. Solo pide las credenciales necesarias: `email` y `password`.
- **`AuthResponse`**: Es el sobre que devolvemos cuando el login es correcto. Solo contiene el `token` (JWT) que el usuario deberá guardar y usar para sus siguientes peticiones.
- **`UserResponse`**: Es la ficha que devuelve el endpoint `/auth/me`. Retorna `id`, `name`, `email` y `createdAt`, **omitiendo deliberadamente el `password`** para garantizar la privacidad y seguridad del usuario.

## 3. `security/` (La bóveda y los guardias)
Esta es la capa encargada de la protección.
- **`SecurityConfig.java`**: Es el jefe de seguridad. Él decide qué endpoints son públicos (como `/auth/login`) y cuáles requieren credenciales (`/patients`).
- **`JwtUtils.java`**: Es la máquina de sellado. Fabrica los tokens JWT firmándolos con nuestra clave secreta y verifica que los tokens que recibimos sigan vigentes (menos de 24 horas).
- **`JwtAuthFilter.java`**: Es el guardia en la puerta. Antes de que cualquier petición llegue a nuestros controladores, este filtro detiene la petición, revisa si trae la cabecera `Authorization: Bearer <token>`, saca el token, lo valida usando `JwtUtils` y si todo es correcto, le permite pasar.

## 4. `service/AuthService.java` y `controller/AuthController.java`
**¿Qué hacen?** Contienen la lógica de negocio para el acceso.
- **AuthService**: Se encarga de verificar que el email no esté tomado al registrar y de comparar las contraseñas al hacer login usando `BCryptPasswordEncoder`.
- **AuthController**: Expone las rutas `/auth/login`, `/auth/register` y `/auth/me` para que los usuarios (o nuestro frontend) puedan interactuar con el sistema de autenticación.
