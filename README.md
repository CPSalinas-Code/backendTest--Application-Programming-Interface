# Proyecto de Microservicios Bancarios

## Descripción General

Este proyecto implementa una arquitectura de microservicios para simular las operaciones básicas de un sistema bancario. La solución está compuesta por dos microservicios principales, una base de datos centralizada, un bus de mensajería para la comunicación asíncrona y un **API Gateway** como punto de entrada único, todo orquestado a través de Docker Compose para un despliegue sencillo.

*   **api-gateway:** Punto de entrada único (`Single Point of Entry`) para todas las solicitudes de los clientes. Enruta las peticiones a los microservicios correspondientes y expone la API al exterior.
*   **microservice-clients:** Gestiona toda la información relacionada con las personas y los clientes (CRUD).
*   **microservice-accounts:** Gestiona las cuentas bancarias y los movimientos asociados (CRUD, lógica de negocio de saldos, reportes).
*   **PostgreSQL:** Actúa como la base de datos única para ambos microservicios.
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
*   Java 21
*   Maven

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
    *   **API Gateway:** `http://localhost:9000` (Punto de entrada para todas las APIs)
    *   **RabbitMQ Management:** `http://localhost:15672` (usuario: `guest`, contraseña: `guest`)

    *Nota: Los microservicios (`clientes` y `cuentas`) no están expuestos directamente. Todas las peticiones deben hacerse a través del API Gateway.*

4.  **Probar los endpoints:**
    Puedes usar una herramienta como Postman o los comandos `curl` proporcionados durante el desarrollo para interactuar con las APIs.

5.  **Detener la aplicación:**
    Para detener y eliminar todos los contenedores, presiona `Ctrl + C` en la terminal donde ejecutaste `docker-compose up` y luego ejecuta:
    ```bash
    docker-compose down
    ```

## Esquema de la Base de Datos

El script para la creación de la base de datos y todas sus tablas se encuentra en `/database/BaseDatos.sql`.

## Guía de Pruebas (Postman)

Para facilitar la prueba de la API, se proporciona una colección de Postman llamada `backTestApi.postman_collection.json`. Puedes importarla en Postman para interactuar con todos los endpoints disponibles.

A continuación se detallan los endpoints y las validaciones clave a tener en cuenta:

### Endpoints a través del API Gateway (`http://localhost:9000`)

Todos los endpoints de los microservicios de `Clientes` y `Cuentas` ahora se acceden a través del API Gateway.

#### Endpoints de Clientes

*   **`POST /api/clientes` (Crear Cliente)**
    *   Crea un nuevo cliente.
    *   **Condición:** Para crear un cliente, se valida el `customerId` (debe ser único), no la cédula de identidad.

*   **`GET /api/clientes/{id}` (Obtener Cliente)**
    *   Obtiene los detalles de un cliente por su `id`.

*   **`PUT /api/clientes/{id}` (Actualizar Cliente)**
    *   Actualiza los datos de un cliente existente.

*   **`DELETE /api/clientes/{id}` (Eliminar Cliente)**
    *   Elimina un cliente por su `id`.

#### Endpoints de Cuentas y Movimientos

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



## NOTA:
* Hubo un ultimo commit luego de la entrega de la solucion **"fix(docker): PostgreSQL Config for docker deploy"** esto ya que al inicio se estaba utilizando SQL Server pero debido a varios problemas en la configuracion de la base de datos no se levantaba correctamente toda la solucion. Al cambiar a PostgreSQL estos problemas fueron resueltos. Cada base de datos o al menos SQL server tienen una manera diferente de configurar su base de datos en Docker