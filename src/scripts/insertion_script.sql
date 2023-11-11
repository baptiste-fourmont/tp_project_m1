-- Connect to stock_management Database
\c stock_management
SET search_path TO stock_schema;

-- Insertion de données dans la table "categories"
INSERT INTO stock_schema.categories (category_name) VALUES
    ('Category 1'),
    ('Category 2'),
    ('Category 3');

-- Insertion de données dans la table "suppliers"
INSERT INTO stock_schema.suppliers (supplier_name) VALUES
    ('Supplier 1'),
    ('Supplier 2'),
    ('Supplier 3');

-- Insertion de données dans la table "products"
INSERT INTO stock_schema.products (product_name, category_id, supplier_id, quantity, price) VALUES
    ('Product A', 1, 1, 100, 10),
    ('Product B', 1, 2, 150, 15),
    ('Product C', 2, 1, 75, 20),
    ('Product D', 2, 3, 120, 25),
    ('Product E', 3, 2, 200, 30);

    -- Insertion de données dans la table "warehouses"
INSERT INTO stock_schema.warehouses (warehouse_name, location) VALUES
    ('Warehouse A', 'Location 1'),
    ('Warehouse B', 'Location 2');

-- Insertion de données dans la table "employees"
INSERT INTO stock_schema.employees (employee_name, department, email, warehouse_id) VALUES
    ('Employee 1', 1, 'employee1@example.com', 1),
    ('Employee 2', 2, 'employee2@example.com', 2),
    ('Employee 3', 1, 'employee3@example.com', 1);

-- Insertion de données dans la table "orders"
INSERT INTO stock_schema.orders (order_date, supplier_id, total_amount) VALUES
    ('2023-01-15', 1, 500),
    ('2023-02-20', 2, 700);

-- Insertion de données dans la table "order_details"
INSERT INTO stock_schema.order_details (order_id, product_id, quantity, unit_price) VALUES
    (1, 1, 50, 10.0),
    (1, 2, 75, 15.0),
    (2, 3, 60, 20.0),
    (2, 4, 90, 25.0);

-- Insertion de données dans la table "product_warehouse"
INSERT INTO stock_schema.product_warehouse (product_id, warehouse_id, quantity) VALUES
    (1, 1, 50),
    (2, 1, 25),
    (3, 2, 30),
    (4, 2, 40),
    (5, 1, 100);
