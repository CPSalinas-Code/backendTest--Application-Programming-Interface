CREATE DATABASE customerperson_db;
GO

USE customerperson_db;
GO

CREATE TABLE person (
                        id BIGINT IDENTITY(1,1) PRIMARY KEY,
                        name VARCHAR(255),
                        gender VARCHAR(255),
                        age INT,
                        identification VARCHAR(255) NOT NULL UNIQUE,
                        address VARCHAR(255),
                        phone VARCHAR(255)
);
GO

CREATE TABLE customers (
                           id BIGINT PRIMARY KEY, -- Mismo valor que en person.id
                           customer_id VARCHAR(255) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           status BIT NOT NULL,
                           CONSTRAINT FK_customers_person FOREIGN KEY (id) REFERENCES people(id)
);
GO