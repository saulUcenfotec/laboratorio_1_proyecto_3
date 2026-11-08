# Laboratorio 2: productos y categorias

Cliente Angular 17 en `frontend/` y API Spring Boot en la raiz del repositorio. La aplicacion usa PostgreSQL local mediante Docker Compose.

## Inicio rapido

Requisitos: JDK 21, Node.js 20 o superior y Docker Desktop.

1. Desde la raiz: `docker compose up -d`.
2. Inicia la API: `mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run`.
3. En otra terminal: `cd frontend`, `npm install` y `npm start`.
4. Abre `http://localhost:4200`.

Credenciales iniciales: `admin` / `Admin123!` y `usuario` / `User123!`. Se pueden configurar con las variables `ADMIN_USERNAME`, `ADMIN_PASSWORD`, `USER_USERNAME` y `USER_PASSWORD`. Las contrasenas se guardan con BCrypt.

## Funciones y permisos

- Los usuarios autenticados pueden consultar productos y categorias.
- Solo `SUPER-ADMIN-ROLE` puede crear, editar o eliminar productos y categorias.
- Cualquier persona puede registrar una cuenta; las nuevas cuentas reciben el rol `USER`.
- Solo `SUPER-ADMIN-ROLE` puede consultar usuarios registrados. La API tambien aplica este permiso.
- El inicio de sesion usa JWT enviado en el encabezado `Authorization`.

## Base de datos

PostgreSQL 16 local en `localhost:5432`; Docker guarda los datos en `laboratorio1_postgres_data`. Configuracion: usuario `laboratorio1_app`, base `laboratorio1`, contrasena `Laboratorio1Local_2026!`. Puedes cambiarla con `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.

## Entrega Moodle

El JSON debe llamarse `laboratorio_2_saul_rojas.json` y contener `nombreEstudiantes`, `gitUrl` y `hashCommitId`. Usa el hash del commit exacto que entregaras. Confirma en Moodle que la fecha del commit cumple el plazo publicado por el profesor.
