/*
 * @Brief: Install and configure the Database
*/

CREATE USER stockapp WITH ENCRYPTED PASSWORD 'yourpass';
CREATE DATABASE stock_management OWNER stockapp;
-- Connect to the database stock_management
\c stock_management


CREATE TABLE IF NOT EXISTS categories (
    category_id SERIAL,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS suppliers (
    supplier_id SERIAL,
    supplier_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (supplier_id)
);

CREATE TABLE IF NOT EXISTS products (
    product_id SERIAL,
    product_name VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    CONSTRAINT CHK_Product CHECK (quantity >= 0 AND price >= 0)
);


CREATE TYPE tr_type AS ENUM ('BUY', 'SELL');

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id SERIAL,
    product_id BIGINT NOT NULL,
    transaction_type tr_type NOT NULL,
    quantity_changed INT NOT NULL,
    transaction_date DATE NOT NULL,
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    CONSTRAINT CHK_Transaction CHECK (quantity_changed >= 0)
);

/* 
* Archivées les transactions qui ont plus d'un an
*/
-- Ensure the archived_transactions table exists with the correct structure
CREATE TABLE IF NOT EXISTS archived_transactions (
    id SERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL,
    date_transac DATE NOT NULL,
    transaction_type tr_type NOT NULL
);

-- Create or replace the procedure
CREATE OR REPLACE PROCEDURE logging_transaction()
LANGUAGE PLPGSQL AS $$
BEGIN
    INSERT INTO archived_transactions (transaction_id, date_transac, transaction_type)
    SELECT transaction_id, transaction_date, transaction_type
    FROM transactions
    WHERE transaction_date < CURRENT_DATE - INTERVAL '1 year';

    DELETE FROM transactions
    WHERE transaction_date < CURRENT_DATE - INTERVAL '1 year';
END $$;

-- Call the logging_transaction procedure
CALL logging_transaction();

-- Check the content of the archived_transactions table
SELECT * FROM archived_transactions;
