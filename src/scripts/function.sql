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


CREATE OR REPLACE FUNCTION add_to_stock(product_id bigint, quantity_added int) RETURNS void AS $$
BEGIN
  UPDATE stock_schema.products SET quantity = quantity + quantity_added WHERE product_id = product_id;
  COMMIT;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION remove_from_stock(product_id bigint, quantity_removed int) RETURNS void AS $$
BEGIN
  UPDATE stock_schema.products SET quantity = quantity - quantity_removed WHERE product_id = product_id;
  COMMIT;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION update_stock(product_id bigint, new_quantity int) RETURNS void AS $$
BEGIN
  UPDATE stock_schema.products SET quantity = new_quantity WHERE product_id = product_id;
  COMMIT;
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
BEGIN
    UPDATE stock_schema.products
    SET quantity = quantity + NEW.quantity
    WHERE product_id = NEW.product_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger à appliquer après l'insertion dans la table des produits
CREATE TRIGGER trig_add_to_stock
AFTER INSERT ON stock_schema.product_warehouse
FOR EACH ROW
EXECUTE FUNCTION add_to_stock();

-- Trigger pour déduire la quantité du stock lorsqu'un produit est retiré d'un entrepot
CREATE OR REPLACE FUNCTION remove_from_stock()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE stock_schema.products
    SET quantity = quantity - NEW.quantity
    WHERE product_id = NEW.product_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger à appliquer après la suppression dans la table des produits dans un entrepôt
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
BEFORE INSERT ON stock_schema.order_details
FOR EACH ROW
EXECUTE FUNCTION check_stock_on_order();
