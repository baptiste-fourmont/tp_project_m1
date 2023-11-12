-- Connect to stock_management Database
\c stock_management
SET search_path TO stock_schema;

CREATE OR REPLACE PROCEDURE logging_transaction()
LANGUAGE PLPGSQL AS $$
BEGIN
    INSERT INTO stock_schema.archived_transactions (transaction_id, date_transac, transaction_type)
    SELECT transaction_id, transaction_date, transaction_type
    FROM stock_schema.transactions
    WHERE transaction_date < CURRENT_DATE - INTERVAL '1 year';

    DELETE FROM stock_schema.transactions
    WHERE transaction_date < CURRENT_DATE - INTERVAL '1 year';
END $$;

-- Call the logging_transaction procedure
CALL logging_transaction();

-- Check the content of the archived_transactions table
SELECT * FROM stock_schema.archived_transactions;

-- Contrôle de la mise à jour de prix
CREATE TABLE stock_schema.product_price_history (
    product_id BIGINT,
    old_price INT,
    new_price INT,
    change_date DATE
);

CREATE OR REPLACE FUNCTION update_product_price()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO stock_schema.product_price_history (product_id, old_price, new_price, change_date)
    VALUES (NEW.product_id, OLD.price, NEW.price, CURRENT_DATE);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_update_product_price
AFTER UPDATE ON stock_schema.products
FOR EACH ROW
EXECUTE FUNCTION update_product_price();

CREATE OR REPLACE FUNCTION add_to_stock(product_id BIGINT, quantity_added INT) 
RETURNS void AS $$
BEGIN
    IF quantity_added < 0 THEN
        RAISE EXCEPTION 'Quantity to add must be positive';
    END IF;

    UPDATE stock_schema.products 
    SET quantity = quantity + quantity_added 
    WHERE products.product_id = product_id;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION remove_from_stock(product_id BIGINT, quantity_removed INT) 
RETURNS void AS $$
DECLARE
    current_quantity INT;
BEGIN
    IF quantity_removed < 0 THEN
        RAISE EXCEPTION 'Quantity to remove must be positive';
    END IF;

    SELECT quantity INTO current_quantity FROM stock_schema.products WHERE products.product_id = product_id;
    IF current_quantity < quantity_removed THEN
        RAISE EXCEPTION 'Insufficient stock for this operation';
    END IF;

    UPDATE stock_schema.products 
    SET quantity = quantity - quantity_removed 
    WHERE products.product_id = product_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_stock(product_id BIGINT, new_quantity INT) 
RETURNS void AS $$
BEGIN
    IF new_quantity < 0 THEN
        RAISE EXCEPTION 'New quantity must be non-negative';
    END IF;

    UPDATE stock_schema.products 
    SET quantity = new_quantity 
    WHERE products.product_id = product_id;
END;
$$ LANGUAGE plpgsql;

-- Création d'un trigger pour contrôler le stock après chaque transaction
CREATE OR REPLACE FUNCTION update_product_quantity()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        UPDATE stock_schema.products
        SET quantity = quantity + NEW.quantity_changed
        WHERE product_id = NEW.product_id;
    ELSIF TG_OP = 'DELETE' THEN
        UPDATE stock_schema.products
        SET quantity = quantity - OLD.quantity_changed
        WHERE product_id = OLD.product_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Application du trigger à la table des transactions
CREATE TRIGGER trig_update_product_quantity
AFTER INSERT OR UPDATE OR DELETE ON stock_schema.transactions
FOR EACH ROW
EXECUTE FUNCTION update_product_quantity();

/*
-- Insérer une nouvelle transaction (ajoute 10 à la quantité de stock d'un produit)
INSERT INTO stock_schema.transactions (product_id, transaction_type, quantity_changed, transaction_date, employee_id)
VALUES (1, 'BUY', 10, CURRENT_DATE, 1);

-- Mettre à jour une transaction existante (augmente de 5 la quantité de stock d'un produit)
UPDATE stock_schema.transactions
SET quantity_changed = 5
WHERE transaction_id = 1;

-- Supprimer une transaction (retire 3 de la quantité de stock d'un produit)
DELETE FROM stock_schema.transactions
WHERE transaction_id = 1;
*/


-- Trigger pour ajuter la quantité de stock lors de l'ajout de produits
CREATE OR REPLACE FUNCTION add_to_stock()
RETURNS TRIGGER AS $$
DECLARE
    total_in_warehouses INT;
BEGIN
    -- Calculer la somme totale du produit dans tous les entrepôts
    SELECT SUM(quantity) INTO total_in_warehouses
    FROM stock_schema.product_warehouse
    WHERE product_id = NEW.product_id;

    -- Vérifier si la quantité totale dans les entrepôts dépasse la quantité disponible dans 'products'
    IF total_in_warehouses + NEW.quantity > (SELECT quantity FROM stock_schema.products WHERE product_id = NEW.product_id) THEN
        RAISE EXCEPTION 'Adding quantity exceeds total product quantity in stock';
    END IF;

    -- Si tout va bien, pas besoin de mettre à jour 'products' ici car cela devrait être géré ailleurs
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trig_add_to_stock
AFTER INSERT OR UPDATE ON stock_schema.product_warehouse
FOR EACH ROW
EXECUTE FUNCTION add_to_stock();

-- Modification de la fonction pour retirer du stock
CREATE OR REPLACE FUNCTION remove_from_stock()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE stock_schema.products
    SET quantity = quantity - OLD.quantity
    WHERE product_id = OLD.product_id;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger pour retirer du stock lorsqu'un produit est retiré d'un entrepôt
CREATE TRIGGER trig_remove_from_stock
AFTER DELETE ON stock_schema.product_warehouse
FOR EACH ROW
EXECUTE FUNCTION remove_from_stock();

-- Vérification de quantité de stock avant une commande
CREATE OR REPLACE FUNCTION check_stock_on_order()
RETURNS TRIGGER AS $$
DECLARE
    current_stock INT;
BEGIN
    SELECT quantity
    INTO current_stock
    FROM stock_schema.products
    WHERE product_id = NEW.product_id;

    IF current_stock >= NEW.quantity THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Insufficient stock for this order';
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Trigger à appliquer avant l'insertion dans la table des détails de commande

CREATE TRIGGER trig_check_stock_on_order
BEFORE INSERT OR UPDATE ON stock_schema.order_details
FOR EACH ROW
EXECUTE FUNCTION check_stock_on_order();

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA stock_schema TO stockapp;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA stock_schema TO stockapp;

-- Grant execute rights on the logging_transaction procedure
GRANT EXECUTE ON PROCEDURE stock_schema.logging_transaction() TO stockapp;
-- Grant execute rights on the update_product_price function
GRANT EXECUTE ON FUNCTION stock_schema.update_product_price() TO stockapp;

-- Grant execute rights on the add_to_stock, remove_from_stock, and update_stock functions
GRANT EXECUTE ON FUNCTION stock_schema.add_to_stock(BIGINT, INT) TO stockapp;
GRANT EXECUTE ON FUNCTION stock_schema.remove_from_stock(BIGINT, INT) TO stockapp;
GRANT EXECUTE ON FUNCTION stock_schema.update_stock(BIGINT, INT) TO stockapp;

-- Grant execute rights on the update_product_quantity function
GRANT EXECUTE ON FUNCTION stock_schema.update_product_quantity() TO stockapp;

-- Grant execute rights on the add_to_stock and remove_from_stock functions (triggers)
GRANT EXECUTE ON FUNCTION stock_schema.add_to_stock() TO stockapp;
GRANT EXECUTE ON FUNCTION stock_schema.remove_from_stock() TO stockapp;

-- Grant execute rights on the check_stock_on_order function
GRANT EXECUTE ON FUNCTION stock_schema.check_stock_on_order() TO stockapp;
