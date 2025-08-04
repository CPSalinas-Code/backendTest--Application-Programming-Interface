USE customerperson_db;

CREATE TABLE person (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(255),
    age INT,
    identification VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(255)
);


CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    customer_id VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status BIT NOT NULL,
    CONSTRAINT FK_customers_person FOREIGN KEY (id) REFERENCES person(id)
);


CREATE TABLE accounts (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    account_number NVARCHAR(50) NOT NULL UNIQUE,
    account_type NVARCHAR(50) NOT NULL,
    initial_balance DECIMAL(19, 4) NOT NULL,
    status BIT NOT NULL,
    customer_id BIGINT NOT NULL
);


CREATE TABLE movements (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    date DATETIME2 NOT NULL,
    movement_type NVARCHAR(50) NOT NULL,
    value DECIMAL(19, 4) NOT NULL,
    balance DECIMAL(19, 4) NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT FK_Movements_Accounts FOREIGN KEY (account_id)
    REFERENCES accounts(id)
);

CREATE INDEX IX_Movements_AccountId ON movements(account_id);
CREATE INDEX IX_Accounts_CustomerId ON accounts(customer_id);
