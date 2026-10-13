# Laboratorio 1

API REST de productos y categorías con Spring Boot, Spring Data JPA, MySQL, Spring Security y JWT.

## Estructura del código

- `controllers`: rutas HTTP y validación de entrada.
- `dto`: objetos que la API recibe y devuelve.
- `mappers`: conversión de entidades a respuestas JSON.
- `model`: entidades persistidas en la base de datos.
- `repository`: acceso a datos con Spring Data JPA.
- `security`: autenticación JWT, autorización y CORS.
- `service`: lógica de negocio y carga inicial de roles/usuarios.

## Requisitos y arranque

1. Inicia Docker Desktop.
2. Desde la carpeta del proyecto, ejecuta `docker compose up -d`. Esto arranca MySQL 8.4 en el puerto local `3307` (puerto `3306` dentro del contenedor) y guarda los datos en el volumen `laboratorio1_mysql_data`.
3. Cuando MySQL esté listo, inicia la API con `mvnw.cmd spring-boot:run` (JDK 21 o superior).

La base de datos se llama `laboratorio1`. El contenedor crea la base y el usuario de aplicación la primera vez. Hibernate crea o actualiza las tablas al iniciar Spring; no se precargan categorías.

Credenciales locales definidas en `docker-compose.yml`:

- Usuario de aplicación: `laboratorio1_app` / `Laboratorio1Local_2026!`
- Administrador de MySQL: `root` / `Laboratorio1Root_2026!`

La aplicación usa por defecto el usuario de aplicación. Si cambias esas credenciales, actualiza `DB_USERNAME` y `DB_PASSWORD` en el entorno donde inicies Spring para que coincidan. `docker compose down` detiene el contenedor y conserva los datos; `docker compose down -v` también elimina el volumen y la base de datos.

Al iniciar Spring se insertan los roles `SUPER-ADMIN-ROLE` y `USER`, y un usuario por rol, solo si todavía no existen. Cuentas iniciales: `admin` / `Admin123!` y `usuario` / `User123!`. Se pueden cambiar con `ADMIN_USERNAME`, `ADMIN_PASSWORD`, `USER_USERNAME` y `USER_PASSWORD`. Las contraseñas se guardan usando BCrypt.

## API

- `POST /api/auth/login`: recibe `{"username":"admin","password":"Admin123!"}` y devuelve un JWT.
- `GET /api/auth/csrf`: devuelve el token CSRF para usarlo como encabezado `X-XSRF-TOKEN` en solicitudes que modifican datos.
- `GET /api/categorias` y `GET /api/productos`: requieren JWT de cualquier usuario autenticado.
- POST, PUT y DELETE para `/api/categorias` y `/api/productos`: requieren `SUPER-ADMIN-ROLE` y JWT.

Las solicitudes de producto requieren `nombre`, `descripcion`, `precio`, `cantidadStock` y `categoriaId`. La categoría debe existir; crea una primero.

Importa `postman/Laboratorio1.postman_collection.json` en Postman y ejecuta las solicitudes en orden. La colección guarda automáticamente el JWT, el CSRF y los IDs creados.
