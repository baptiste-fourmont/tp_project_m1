/*
* Create Database for stockapp
* \dn show schema
*/
-- Créer l'utilisateur stockapp et la base de données stock_management
CREATE USER stockapp WITH ENCRYPTED PASSWORD 'yourpass';
CREATE DATABASE stock_management OWNER stockapp;

-- Accorder à l'utilisateur stockapp la permission de se connecter à la base de données
GRANT CONNECT ON DATABASE stock_management TO stockapp;

-- Se connecter à la base de données stock_management
\c stock_management

-- Créer le schéma stock_schema et définir l'utilisateur stockapp comme propriétaire
CREATE SCHEMA IF NOT EXISTS stock_schema AUTHORIZATION stockapp;

-- Changer le chemin de recherche par défaut pour le schéma stock_schema
SET search_path TO stock_schema;

-- Accorder des privilèges à l'utilisateur stockapp sur toutes les tables du schéma stock_schema
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA stock_schema TO stockapp;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'tr_type') THEN
        CREATE TYPE tr_type AS ENUM('BUY', 'SELL');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS stock_schema.categories (
    category_id SERIAL,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS stock_schema.suppliers (
    supplier_id SERIAL,
    supplier_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (supplier_id)
);

CREATE TABLE IF NOT EXISTS stock_schema.warehouses (
    warehouse_id SERIAL,
    warehouse_name VARCHAR(255),
    location VARCHAR(255),
    PRIMARY KEY (warehouse_id)
);

CREATE TABLE IF NOT EXISTS stock_schema.products (
    product_id SERIAL,
    product_name VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES stock_schema.categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES stock_schema.suppliers(supplier_id),
    CONSTRAINT CHK_Product CHECK (quantity >= 0 AND price >= 0)
);

CREATE TABLE IF NOT EXISTS stock_schema.product_warehouse (
    product_warehouse_id SERIAL,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (product_warehouse_id),
    FOREIGN KEY (product_id) REFERENCES stock_schema.products(product_id),
    FOREIGN KEY (warehouse_id) REFERENCES stock_schema.warehouses(warehouse_id),
    CONSTRAINT CHK_Product_Warehouse CHECK (quantity >= 0)
);


CREATE TABLE IF NOT EXISTS stock_schema.employees (
    employee_id SERIAL,
    employee_name VARCHAR(255) NOT NULL,
    department INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    warehouse_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id),
    FOREIGN KEY (warehouse_id) REFERENCES stock_schema.warehouses(warehouse_id)
);

CREATE TABLE IF NOT EXISTS stock_schema.transactions (
    transaction_id SERIAL,
    product_id BIGINT NOT NULL,
    transaction_type tr_type NOT NULL,
    quantity_changed INT NOT NULL,
    transaction_date DATE NOT NULL,
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (product_id) REFERENCES stock_schema.products(product_id),
    FOREIGN KEY (employee_id) REFERENCES stock_schema.employees(employee_id),
    CONSTRAINT CHK_Transaction CHECK (quantity_changed >= 0)
);

CREATE TABLE IF NOT EXISTS stock_schema.orders (
    order_id SERIAL,
    order_date DATE NOT NULL,
    supplier_id BIGINT NOT NULL,
    total_amount INT NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (supplier_id) REFERENCES stock_schema.suppliers(supplier_id)
);

CREATE TABLE IF NOT EXISTS stock_schema.order_details (
    order_detail_id SERIAL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price FLOAT NOT NULL,
    PRIMARY KEY (order_detail_id),
    FOREIGN KEY (order_id) REFERENCES stock_schema.orders(order_id),
    FOREIGN KEY (product_id) REFERENCES stock_schema.products(product_id)
);

/* 
* Archivées les transactions qui ont plus d'un an
*/
-- Ensure the archived_transactions table exists with the correct structure
CREATE TABLE IF NOT EXISTS stock_schema.archived_transactions (
    id SERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL,
    date_transac DATE NOT NULL,
    transaction_type tr_type NOT NULL
);


/*
* Optimisation B-Index
*/

-- Indexation des clés étrangères
CREATE INDEX idx_products_category_id ON stock_schema.products USING btree (category_id);
CREATE INDEX idx_products_supplier_id ON stock_schema.products USING btree (supplier_id);
CREATE INDEX idx_product_warehouse_product_id ON stock_schema.product_warehouse USING btree (product_id);
CREATE INDEX idx_product_warehouse_warehouse_id ON stock_schema.product_warehouse USING btree (warehouse_id);

/*
* On s'assure que les tables dans le schéma sont juste et les permissions aussi
*/

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA stock_schema TO stockapp;


\dn 

SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'stock_schema';

SELECT grantor, grantee, table_catalog, table_schema, table_name, privilege_type, is_grantable, with_hierarchy
FROM information_schema.table_privileges
WHERE grantee = 'stockapp' AND table_schema = 'stock_schema';;
