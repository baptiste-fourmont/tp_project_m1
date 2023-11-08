/*
 * @Brief: Install and configure the Database
*/

CREATE USER stockapp WITH ENCRYPTED PASSWORD 'yourpass';
CREATE DATABASE stock_management OWNER stockapp;
-- Connect to the database stock_management
\c stock_management

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