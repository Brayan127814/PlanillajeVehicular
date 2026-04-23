# 🚗 Sistema de Planillaje Vehicular

Sistema backend desarrollado para la gestión de ingreso y salida de vehículos, con control de usuarios, autenticación y registro de operaciones.

---

## 📌 Descripción

Este proyecto permite registrar, consultar y administrar el planillaje vehicular dentro de una organización. Incluye autenticación de usuarios, control de accesos y persistencia de datos en base de datos relacional.

---

## ⚙️ Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* PostgreSQL
* Maven

---

## 🔐 Funcionalidades principales

* Registro de vehículos
* Registro de planillaje (entrada/salida)
* Autenticación de usuarios con JWT
* Control de acceso por roles
* Persistencia en base de datos
* API REST estructurada

---

## 🧱 Estructura del proyecto

* `config` → configuración de seguridad (JWT, filtros)
* `entidades` → entidades JPA
* `repositorios` → acceso a datos
* `servicios` → lógica de negocio
* `controladores` → endpoints REST

---

## 🚀 Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Brayan127814/PlanillajeVehicular.git
cd planillaje-vehicular
```

### 2. Configurar variables de entorno

En PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/planillaje_db"
$env:DB_USER="postgres"
$env:DB_PASSWORD="tu_password"
```

### 3. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

---

## 📡 Endpoints principales (ejemplo)

| Método | Endpoint    | Descripción              |
| ------ | ----------- | ------------------------ |
| POST   | /auth/login | Autenticación            |
| POST   | /vehiculos  | Registrar vehículo       |
| POST   | /planillaje | Registrar entrada/salida |
| GET    | /planillaje | Consultar registros      |

---

## 🛡️ Seguridad

El sistema implementa autenticación basada en JWT, asegurando que solo usuarios autorizados puedan acceder a los endpoints protegidos.

---

## 📌 Estado del proyecto

✅ Funcional
🚧 En mejora continua

---

## 👨‍💻 Autor

Brayan Castillo
Proyecto desarrollado como práctica profesional en backend con Spring Boot.

---
