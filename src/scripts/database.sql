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

CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL,
    order_date DATE NOT NULL,
    supplier_id BIGINT NOT NULL,
    total_amount INT NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

CREATE TABLE IF NOT EXISTS order_details (
    order_detail_id SERIAL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price FLOAT NOT NULL,
    PRIMARY KEY (order_detail_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS warehouses (
    warehouse_id SERIAL,
    warehouse_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    PRIMARY KEY (warehouse_id)
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id SERIAL,
    employee_name VARCHAR(255) NOT NULL,
    department INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    warehouse_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id)
);

CREATE TABLE IF NOT EXISTS warehouses (
    product_warehouse_id SERIAL,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    PRIMARY KEY (product_warehouse_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(warehouse_id)
);

