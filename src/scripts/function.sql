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