CREATE TABLE person (
                        id BIGSERIAL PRIMARY KEY,
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
                           status BOOLEAN NOT NULL,
                           CONSTRAINT FK_customers_person FOREIGN KEY (id) REFERENCES person(id)
);


CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          account_number VARCHAR(50) NOT NULL UNIQUE,
                          account_type VARCHAR(50) NOT NULL,
                          initial_balance DECIMAL(19, 4) NOT NULL,
                          status BOOLEAN NOT NULL,
                          customer_id BIGINT NOT NULL
);


CREATE TABLE movements (
                           id BIGSERIAL PRIMARY KEY,
                           date TIMESTAMP NOT NULL,
                           movement_type VARCHAR(50) NOT NULL,
                           value DECIMAL(19, 4) NOT NULL,
                           balance DECIMAL(19, 4) NOT NULL,
                           account_id BIGINT NOT NULL,
                           CONSTRAINT FK_Movements_Accounts FOREIGN KEY (account_id)
                               REFERENCES accounts(id)
);

CREATE INDEX IX_Movements_AccountId ON movements(account_id);
CREATE INDEX IX_Accounts_CustomerId ON accounts(customer_id);
