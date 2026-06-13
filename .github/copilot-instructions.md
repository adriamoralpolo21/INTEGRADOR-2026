<!-- Instrucciones específicas para agentes AI que trabajan en este repositorio Spring Boot -->
# Copilot instructions (project-specific)

Este repositorio es una aplicación Spring Boot mínima (Java 21) que usa:
- Spring Web, Thymeleaf, Spring Security, Spring Data JPA (MySQL).

Principales puntos para un agente AI:

- **Arquitectura / fronteras**: código fuente en `src/main/java/com/glovo/app`.
  - Controladores: `controllers/` (rutas públicas y protegidas). Ej.: `AuthController`, `SoporteController`.
  - Servicios: `services/` (lógica de negocio). Ej.: `UsuarioService`, `SoporteService`.
  - Repositorios JPA: `repository/` (persistencia). Ej.: `UsuarioRepository`.
  - Entidades: `entity/` y DTOs en `dto/`.

- **Flujo de registro / validaciones**:
  - Registro normaliza email (trim + lowercase) y teléfono (solo dígitos) en `UsuarioService.registrar`.
  - Validadores personalizados: `validation/UniqueEmail` y `UniquePhone` usan `UsuarioRepository.existsBy...`.
  - Contraseñas con `BCryptPasswordEncoder` (ver `SecurityConfig.passwordEncoder`).

- **Seguridad** (`SecurityConfig`) — puntos clave:
  - Rutas públicas listadas explícitamente: `/`, `/login`, `/register`, `/soporte`, `/ofrecemos`, `/comida`, `/tiendas`, `/farmacia`, `/supermercado`.
  - Recursos estáticos servidos desde `/css/**`, `/js/**`, `/images/**`, `/imgs/**`, `/webjars/**`.
  - CSRF ignorado para `/register` y `/cart/**` (chequear si se cambia el comportamiento).
  - Login personalizado en `/login`, `defaultSuccessUrl("/", true)`.

- **Plantillas / recursos**:
  - Thymeleaf templates en `src/main/resources/templates/` (ej.: `login.html`, `register.html`, `soporte.html`).
  - `application.properties` desactiva cache de Thymeleaf (útil para desarrollo).

- **Dependencias externas / runtime**:
  - MySQL es la base por defecto: `spring.datasource.*` en `application.properties`.
  - Local DB: `jdbc:mysql://localhost:3306/proyecto_app` (contraseña hardcodeada en `application.properties` — ten cuidado con cambios de entorno).

- **Comandos comunes (Windows PowerShell)**:
  - Construir: `./mvnw.cmd -DskipTests package`  (desde la carpeta `app`)
  - Ejecutar en dev: `./mvnw.cmd spring-boot:run` o `java -jar target/app-0.0.1-SNAPSHOT.jar`
  - Ejecutar tests: `./mvnw.cmd test`

- **Convenciones de código observables**:
  - Lombok (constructores/Builders) usado en entidades y servicios.
  - Normalización/validación ocurre en servicio y validadores; evita duplicar la lógica en controladores.
  - Mensajes de error para `registrar` se exponen mediante `BindingResult` en el `AuthController`.

- **Qué revisar antes de modificar**:
  - Cambios en entidades que afectan unique constraints (email/telefono) deben alinearse con `UsuarioRepository` y los mensajes de `UsuarioService`.
  - Al tocar seguridad, revisar `SecurityConfig` (CSRF, rutas permitidas, login/logout).
  - Si agregas endpoints que deben ser públicos, actualiza la lista de `requestMatchers`.

Ejemplos rápidos:
- Para encontrar la lógica de registro: buscar `UsuarioService.registrar` y `AuthController.processRegister`.
- Para ver validaciones personalizadas: `validation/UniqueEmail` y `UniquePhone`.

Si falta información (por ejemplo: comandos de despliegue, variables de entorno, o pipelines CI), pregúntame y lo añado.
