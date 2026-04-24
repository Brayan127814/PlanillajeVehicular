# Sistema de Planillaje Vehicular

Backend REST desarrollado con Java 21 y Spring Boot para administrar el control diario de vehiculos en parqueaderos o puestos operativos de una empresa. El sistema permite registrar empresas, crear puestos, generar parqueaderos, invitar usuarios, autenticar con JWT, asignar vehiculos a espacios disponibles y registrar el planillaje vehicular con novedades, detalle y fotos en Base64.

## Descripcion del proyecto

Planillaje Vehicular centraliza la operacion de control de parqueaderos por empresa y puesto. Cada empresa puede tener uno o varios puestos; cada puesto define su cantidad de parqueaderos; los usuarios quedan asociados a una empresa y, cuando aplica, a un puesto especifico. A partir de esa estructura, los usuarios autorizados pueden registrar vehiculos, ocupar o liberar parqueaderos y crear planillajes diarios por placa.

El proyecto esta pensado como una API backend para ser consumida por un frontend web o movil. Expone endpoints REST, usa PostgreSQL como base de datos, Spring Data JPA para persistencia, Spring Security para proteger recursos y tokens JWT para mantener sesiones stateless.

## Funcionalidad principal

- Registro de empresas junto con su usuario administrador.
- Inicio de sesion con usuario y contrasena.
- Generacion de `AccessToken` y `RefreshToken` mediante JWT.
- Proteccion de endpoints con autenticacion y permisos.
- Creacion de puestos asociados a la empresa del usuario autenticado.
- Listado paginado de puestos por empresa.
- Creacion masiva de parqueaderos segun el total definido en cada puesto.
- Consulta paginada de parqueaderos disponibles u ocupados por puesto.
- Liberacion de parqueaderos ocupados.
- Creacion de invitaciones por puesto para registrar nuevos usuarios.
- Registro de usuarios mediante token de invitacion de un solo uso.
- Listado paginado de usuarios por puesto.
- Registro de vehiculos con placa, marca y parqueadero asignado.
- Validacion de disponibilidad del parqueadero antes de ocuparlo.
- Busqueda y listado paginado de vehiculos por puesto.
- Registro de planillaje vehicular diario por placa.
- Validacion para evitar mas de un planillaje diario por vehiculo.
- Registro de novedades del planillaje: `OK`, `RAYON`, `GOLPE`, `SUMIDO`, `OTRO`.
- Registro opcional de detalle cuando la novedad lo requiere.
- Almacenamiento de fotos del planillaje en formato Base64.
- Consulta paginada de planillajes del dia por puesto.
- Consulta paginada de planillajes por placa.
- Conteo de planillajes por fecha.
- Manejo centralizado de errores y respuestas de acceso denegado/no autenticado.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3.5.8
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Validation
- JWT con `jjwt`
- PostgreSQL
- Maven
- Lombok
- Docker y Docker Compose

## Arquitectura del proyecto

```text
src/main/java/planillaje/Vehicular/planillaje/vehicular
|-- config          # Seguridad, CORS, JWT, carga inicial de datos
|-- controladores   # Endpoints REST
|-- dtos            # Objetos de entrada y salida de la API
|-- entidades       # Entidades JPA
|-- enums           # Estados y novedades del dominio
|-- Excepciones     # Excepciones personalizadas y handler global
|-- Interfaces      # Contratos de servicios
|-- mapper          # Conversion entre entidades y DTOs
|-- respositorios   # Repositorios JPA
|-- servicios       # Logica de negocio
|-- validaciones    # Validaciones de apoyo
```

## Modelo de negocio

- **Empresa**: organizacion propietaria de puestos, usuarios y operacion de planillaje.
- **Puesto**: sede o punto operativo de una empresa. Define direccion y total de parqueaderos.
- **Parqueadero**: espacio numerado dentro de un puesto. Puede estar `VACIO` u `OCUPADO`.
- **Usuario**: persona que accede al sistema. Pertenece a una empresa y opcionalmente a un puesto.
- **Rol y permisos**: controlan las acciones disponibles sobre usuarios, puestos, invitaciones, vehiculos y planillajes.
- **Invitacion**: token de un solo uso para registrar usuarios en una empresa y puesto especificos.
- **Vehiculo**: vehiculo identificado por placa y marca, asociado a un parqueadero.
- **Planillaje**: registro diario de revision de un vehiculo, con usuario, puesto, parqueadero, novedad, detalle y fotos.

## Seguridad

El sistema usa Spring Security con sesiones stateless. Los endpoints publicos son:

- `POST /usuarios/login`
- `POST /usuarios/registrar`
- `POST /empresas/registrar`

Los demas endpoints requieren autenticacion con JWT en el encabezado:

```http
Authorization: Bearer <AccessToken>
```

Tambien se usan permisos con `@PreAuthorize`, por ejemplo:

- `CREAR_INVITACION`
- `LISTAR_USUARIOS`
- `CREAR_PUESTO`
- `LISTAR_PUESTO`
- `CREAR_PARQUEADEROS`
- `CREAR_VEHICULO`
- `LISTAR_VEHICULO`
- `LISTAR_PLACA`
- `CREAR_PLANILLAJE`
- `LISTAR_PLANILLAJE`

## Datos iniciales

Al iniciar por primera vez, el proyecto crea datos base si no existen roles registrados:

- Empresa demo: `Empresa Demo`
- Usuario administrador:
  - Usuario: `admin`
  - Contrasena: `123456`
- Rol: `ROLE_ADMIN`
- Permisos iniciales para invitaciones, usuarios y puestos.

> Nota: el registro de usuarios por invitacion busca el rol `ROLE_RECORREDOR`. Asegurate de tenerlo creado en la base de datos si vas a registrar usuarios operativos con invitacion.

## Configuracion

La aplicacion lee la configuracion de base de datos desde variables de entorno con valores por defecto:

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/planillaje_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=12345
PORT=8082
```

Archivo principal:

```text
src/main/resources/application.properties
```

## Ejecucion local

### Requisitos

- Java 21
- Maven 3.9+
- PostgreSQL

### Pasos

1. Clonar el repositorio:

```bash
git clone https://github.com/Brayan127814/PlanillajeVehicular.git
cd planillaje-vehicular
```

2. Configurar la base de datos PostgreSQL:

```sql
CREATE DATABASE planillaje_db;
```

3. Configurar variables de entorno si no quieres usar los valores por defecto:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/planillaje_db"
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="12345"
$env:PORT="8082"
```

4. Ejecutar la aplicacion:

```bash
./mvnw spring-boot:run
```

En Windows tambien puedes usar:

```powershell
.\mvnw.cmd spring-boot:run
```

La API quedara disponible en:

```text
http://localhost:8082
```

## Ejecucion con Docker Compose

El proyecto incluye `docker-compose.yml` para levantar PostgreSQL, backend y un frontend externo.

Variables usadas en `.env`:

```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=12345
POSTGRES_DB=planillaje_db
DB_PORT=5432
APP_PORT=8082
FRONTEND_PORT=4200
```

Comando:

```bash
docker compose up --build
```

> El servicio `frontend` apunta a una ruta local configurada en `docker-compose.yml`. Si no tienes ese proyecto frontend en la misma ubicacion, ajusta o comenta ese servicio antes de levantar los contenedores.

## Endpoints principales

### Autenticacion y usuarios

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/usuarios/login` | Inicia sesion y retorna tokens JWT | Publico |
| POST | `/usuarios/registrar` | Registra usuario usando token de invitacion | Publico |
| GET | `/usuarios/{puestoId}` | Lista usuarios de un puesto | Protegido |

### Empresas

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/empresas/registrar` | Registra empresa y usuario administrador | Publico |

### Invitaciones

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/invitaciones/crearInvitacion` | Crea token de invitacion para un puesto | Protegido |

### Puestos

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/puestos/registrar` | Crea un puesto para la empresa autenticada | Protegido |
| GET | `/puestos/allPuestos` | Lista puestos paginados por empresa | Protegido |

### Parqueaderos

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/parqueaderos/registrar` | Crea parqueaderos para un puesto | Protegido |
| GET | `/parqueaderos/parqueaderosPaginados` | Lista parqueaderos vacios del puesto actual | Protegido |
| GET | `/parqueaderos/ocupadosPaginados` | Lista parqueaderos ocupados del puesto actual | Protegido |
| POST | `/parqueaderos/liberar/{parqueaderoId}` | Libera un parqueadero ocupado | Protegido |

### Vehiculos

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/vehiculos/registrar` | Registra vehiculo y ocupa parqueadero | Protegido |
| GET | `/vehiculos/vehiculos-paginados` | Lista vehiculos del puesto actual | Protegido |
| GET | `/vehiculos/placa?placa=ABC123` | Busca vehiculo por placa en el puesto actual | Protegido |
| GET | `/vehiculos/VehiculoPlaca?placa=ABC123` | Busca vehiculo por placa globalmente | Protegido |

### Planillaje vehicular

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| POST | `/planillajeVehicular/registrar` | Registra planillaje diario de un vehiculo | Protegido |
| GET | `/planillajeVehicular/paginados?placa=ABC123` | Lista planillajes por placa | Protegido |
| GET | `/planillajeVehicular/todos` | Lista planillajes del dia del puesto actual | Protegido |
| GET | `/planillajeVehicular/totalPordia?fecha=2026-04-24` | Cuenta planillajes por fecha | Protegido |

## Ejemplos de peticiones

### Login

```http
POST /usuarios/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

Respuesta:

```json
{
  "AccessToken": "token_jwt",
  "RefreshToken": "refresh_token_jwt"
}
```

### Registrar empresa

```http
POST /empresas/registrar
Content-Type: application/json

{
  "nombreEmpresa": "Mi Empresa",
  "nit": "900123456",
  "nombre": "Administrador",
  "username": "admin_empresa",
  "password": "123456"
}
```

### Crear puesto

```http
POST /puestos/registrar
Authorization: Bearer <AccessToken>
Content-Type: application/json

{
  "nombrePuesto": "Sede Centro",
  "direccion": "Calle 10 # 20-30",
  "totalParqueaderos": 20
}
```

### Crear parqueaderos del puesto

```http
POST /parqueaderos/registrar
Authorization: Bearer <AccessToken>
Content-Type: application/json

{
  "puestoId": 1
}
```

### Registrar vehiculo

```http
POST /vehiculos/registrar
Authorization: Bearer <AccessToken>
Content-Type: application/json

{
  "placa": "ABC123",
  "marca": "Toyota",
  "parqueaderoId": 1
}
```

### Registrar planillaje

```http
POST /planillajeVehicular/registrar
Authorization: Bearer <AccessToken>
Content-Type: application/json

{
  "placa": "ABC123",
  "novedadesPlanillaje": "OK",
  "detalle": null,
  "fotoBase64": []
}
```

## Reglas importantes del negocio

- Un puesto solo puede tener sus parqueaderos generados una vez.
- Un vehiculo debe estar asociado a un parqueadero para poder planillarse.
- El usuario solo puede operar sobre el puesto que tiene asignado.
- Un parqueadero ocupado no puede recibir otro vehiculo.
- Al registrar un vehiculo, el parqueadero pasa a estado `OCUPADO`.
- Al liberar un parqueadero, se desvincula el vehiculo y el parqueadero vuelve a `VACIO`.
- Un vehiculo no puede tener mas de un planillaje registrado el mismo dia.
- Si la novedad es `OK`, el detalle debe estar vacio o nulo.
- Si la novedad es `OTRO`, el detalle es obligatorio.
- Las invitaciones son de un solo uso.

## Compilacion

```bash
./mvnw clean package
```

En Windows:

```powershell
.\mvnw.cmd clean package
```

El artefacto generado queda en:

```text
target/planillaje-vehicular-0.0.1-SNAPSHOT.jar
```

## Estado del proyecto

Proyecto funcional en mejora continua. La API ya cuenta con los modulos centrales de empresa, usuarios, seguridad, puestos, parqueaderos, vehiculos y planillaje vehicular.

## Autor

Brayan Castillo

Proyecto desarrollado como practica profesional backend con Spring Boot.
