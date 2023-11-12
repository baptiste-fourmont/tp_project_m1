-- Démarrer une transaction
BEGIN;
    INSERT INTO stock_schema.transactions (transaction_date, employee_id, product_id, quantity_changed, transaction_type)
    VALUES (CURRENT_DATE, 1, 1, 10, 'BUY');
COMMIT;
