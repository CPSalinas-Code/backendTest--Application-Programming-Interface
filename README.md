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
