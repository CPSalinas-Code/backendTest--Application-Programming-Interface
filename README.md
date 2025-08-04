# Proyecto de Microservicios Bancarios

## Descripción General

Este proyecto implementa una arquitectura de microservicios para simular las operaciones básicas de un sistema bancario. La solución está compuesta por dos microservicios principales, una base de datos centralizada y un bus de mensajería para la comunicación asíncrona, todo orquestado a través de Docker Compose para un despliegue sencillo.

*   **microservice-clients:** Gestiona toda la información relacionada con las personas y los clientes (CRUD).
*   **microservice-accounts:** Gestiona las cuentas bancarias y los movimientos asociados (CRUD, lógica de negocio de saldos, reportes).
*   **SQL Server:** Actúa como la base de datos única para ambos microservicios.
*   **RabbitMQ:** Funciona como message broker para la comunicación asíncrona entre servicios.

## Funcionalidades Implementadas (Requisitos)

-   [x] **F1: CRUDs Completos:** Se ha implementado el CRUD para las entidades Cliente, Cuenta y Movimiento.
-   [x] **F2: Registro de Movimientos:** El registro de un movimiento actualiza el saldo disponible de la cuenta.
-   [x] **F3: Control de Saldo:** Se alerta con "Saldo no disponible" si una transacción de débito no es posible (excepto en cuentas corrientes).
-   [x] **F4: Reporte de Estado de Cuenta:** Se ha implementado un endpoint (`/api/reports`) que genera un estado de cuenta por cliente y rango de fechas.
-   [x] **F5: Pruebas Unitarias:** Se ha creado una prueba unitaria para la capa de servicio (`CustomerServiceImplTest`), demostrando el uso de Mocks para probar la lógica de negocio de forma aislada.
-   [x] **F6: Pruebas de Integración:** Se ha creado una prueba de integración (`AccountControllerIntegrationTest`) que levanta un contexto de Spring, prueba un endpoint real y simula dependencias externas.
-   [x] **F7: Despliegue en Contenedores:** Toda la solución está contenerizada y se puede levantar con un solo comando gracias a `Docker Compose`.
-   [x] **Arquitectura Senior:** La solución está implementada en 2 microservicios con comunicación síncrona (OpenFeign) y asíncrona (RabbitMQ).

## Requisitos Previos

*   Git
*   Docker y Docker Compose (Se recomienda Docker Desktop)
*   Java 21 (para ejecutar localmente fuera de Docker)
*   Maven (para ejecutar localmente fuera de Docker)

## Cómo Ejecutar la Solución Completa

La forma recomendada de ejecutar el proyecto es a través de Docker Compose, ya que levanta toda la arquitectura necesaria (2 microservicios, base de datos, message broker) con un solo comando.

1.  **Clonar el repositorio:**
    ```bash
    git clone [URL_DEL_REPOSITORIO]
    cd [NOMBRE_DEL_PROYECTO]
    ```

2.  **Levantar los contenedores:**
    Desde la raíz del proyecto (donde se encuentra `docker-compose.yml`), ejecuta:
    ```bash
    docker-compose up --build
    ```
    *La primera vez, este comando tardará varios minutos en descargar y construir todas las imágenes.*

3.  **Acceder a los servicios:**
    *   **API Clientes:** `http://localhost:8080`
    *   **API Cuentas y Movimientos:** `http://localhost:8081`
    *   **RabbitMQ Management:** `http://localhost:15672` (usuario: `guest`, contraseña: `guest`)

4.  **Probar los endpoints:**
    Puedes usar una herramienta como Postman o los comandos `curl` proporcionados durante el desarrollo para interactuar con las APIs.

5.  **Detener la aplicación:**
    Para detener y eliminar todos los contenedores, presiona `Ctrl + C` en la terminal donde ejecutaste `docker-compose up` y luego ejecuta:
    ```bash
    docker-compose down
    ```

## Esquema de la Base de Datos

El script para la creación de la base de datos y todas sus tablas se encuentra en `/database/BaseDatos.sql`.


# Cómo Ejecutar en Local (Alternativa a Docker)

Recientemente, se han presentado ciertos problemas con la persistencia de datos y la configuración de la base de datos al usar Docker. Por esta razón, se ha añadido una guía detallada para ejecutar el proyecto de forma local. Esta alternativa ofrece un mayor control sobre la base de datos y facilita la depuración.

### 1. Requisitos Previos

Asegúrate de tener instalado lo siguiente:

-   **Java 21:** Para compilar y ejecutar los microservicios.
-   **Maven:** Para la gestión de dependencias y la construcción de los proyectos.
-   **SQL Server:** Una instancia local de SQL Server (cualquier edición, como Express o Developer, es suficiente).
-   **Docker:** (Opcional, pero recomendado) Para levantar RabbitMQ de forma sencilla. Si no usas Docker, necesitarás una instalación local de RabbitMQ.

### 2. Configuración de la Base de Datos

1.  **Conéctate a tu instancia de SQL Server** usando una herramienta como SQL Server Management Studio (SSMS) o Azure Data Studio.

2.  **Crea la base de datos:**
    Ejecuta la siguiente consulta para crear la base de datos que usarán los microservicios:
    ```sql
    CREATE DATABASE customerperson_db;
    ```

3.  **Crea las tablas:**
    Abre el archivo `database/BaseDatos.sql` y ejecuta su contenido en la base de datos `customerperson_db` que acabas de crear. Esto configurará todas las tablas necesarias.

4.  **Verifica las credenciales:**
    Los microservicios están configurados para conectarse a la base de datos con las siguientes credenciales:
    -   **Usuario:** `sa`
    -   **Contraseña:** `dbpasswordtestCS123`

    Asegúrate de que tu instancia de SQL Server tenga un usuario `sa` con esta contraseña o ajusta las propiedades en los archivos `application.properties` de cada microservicio.

### 3. Levantar RabbitMQ

La forma más sencilla de ejecutar RabbitMQ es a través de su imagen oficial de Docker. Abre una terminal y ejecuta el siguiente comando:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

-   Esto iniciará RabbitMQ en segundo plano (`-d`).
-   El puerto `5672` se usará para la comunicación entre los microservicios.
-   El puerto `15672` te dará acceso a la interfaz de gestión web (`http://localhost:15672`, usuario: `guest`, pass: `guest`).

### 4. Ejecutar los Microservicios

Debes levantar los microservicios en el orden correcto, ya que `accounts-app` depende de `clients-app`.

#### a) Microservicio de Clientes (`microservice-clients`)

1.  **Abre una nueva terminal** y navega al directorio del microservicio:
    ```bash
    cd microservice-clients
    ```

2.  **Limpia, compila y ejecuta la aplicación:**
    Usa el wrapper de Maven (`mvnw`) incluido en el proyecto para asegurar que usas la versión correcta.

    *En Windows:*
    ```bash
    ./mvnw spring-boot:run
    ```

    *En macOS/Linux:*
    ```bash
    ./mvnw spring-boot:run
    ```

    Este microservicio se ejecutará en el puerto `8080` y se conectará automáticamente a la base de datos local (`localhost:1433`) si has seguido los pasos anteriores.

#### b) Microservicio de Cuentas y Movimientos (`microservice-accounts`)

1.  **Abre otra terminal** y navega al directorio del microservicio:
    ```bash
    cd microservice-accounts
    ```

2.  **Limpia, compila y ejecuta la aplicación:**
    Este servicio necesita saber dónde encontrar al microservicio de clientes. Le pasamos la URL a través de una propiedad del sistema.

    *En Windows:*
    ```bash
    ./mvnw spring-boot:run -Dspring-boot.run.arguments="--CLIENT_SERVICE_BASE_URL=http://localhost:8080/api"
    ```

    *En macOS/Linux:*
    ```bash
    ./mvnw spring-boot:run -Dspring-boot.run.arguments=--CLIENT_SERVICE_BASE_URL=http://localhost:8080/api
    ```

    Este microservicio se ejecutará en el puerto `8081` y se conectará a la base de datos, a RabbitMQ y al servicio de clientes.

### 5. Verificar que todo funciona

-   **API Clientes:** `http://localhost:8080`
-   **API Cuentas y Movimientos:** `http://localhost:8081`
-   **RabbitMQ Management:** `http://localhost:15672`

Ahora se puede usar Postman o `curl` para probar los endpoints de la misma forma que con la configuración de Docker.



## Guía de Pruebas (Postman)

Para facilitar la prueba de la API, se proporciona una colección de Postman llamada `backTestApi.postman_collection.json`. Puedes importarla en Postman para interactuar con todos los endpoints disponibles.

A continuación se detallan los endpoints y las validaciones clave a tener en cuenta:

### Microservicio de Clientes (`http://localhost:8080`)

*   **`POST /api/clientes` (Crear Cliente)**
    *   Crea un nuevo cliente.
    *   **Condición:** Para crear un cliente, se valida el `customerId` (debe ser único), no la cédula de identidad.

*   **`GET /api/clientes/{id}` (Obtener Cliente)**
    *   Obtiene los detalles de un cliente por su `id`.

*   **`PUT /api/clientes/{id}` (Actualizar Cliente)**
    *   Actualiza los datos de un cliente existente.

*   **`DELETE /api/clientes/{id}` (Eliminar Cliente)**
    *   Elimina un cliente por su `id`.

### Microservicio de Cuentas y Movimientos (`http://localhost:8081`)

*   **`POST /api/accounts` (Crear Cuenta)**
    *   Crea una nueva cuenta para un cliente.
    *   **Condición:** Se valida el `customerId` para asociar la cuenta al cliente correcto.

*   **`PUT /api/accounts/{id}` (Actualizar Cuenta)**
    *   Actualiza los detalles de una cuenta.
    *   **Condición:** Se valida el `customerId` para asegurar que la cuenta pertenece al cliente.

*   **`POST /api/movements` (Crear Movimiento)**
    *   Registra un nuevo movimiento (depósito o retiro).
    *   **Condición:** Se valida el `accountNumber` para aplicar el movimiento a la cuenta correcta, no se usa el `accountId`.

*   **`PUT /api/movements/{id}` (Actualizar Movimiento)**
    *   Actualiza un movimiento existente.
    *   **Condición:** Se debe enviar el `movementId` en la URL para identificar el movimiento a editar.

*   **`GET /api/reports` (Generar Reporte)**
    *   Genera un estado de cuenta para un cliente en un rango de fechas.
    *   Ejemplo: `GET /api/reports?customerId=24&startDate=2025-08-01&endDate=2025-08-04`
