-- Démarrer une transaction
BEGIN;

-- Tenter d'insérer une nouvelle transaction
BEGIN
    INSERT INTO stock_schema.transactions (transaction_date, employee_id, product_id, quantity_changed, transaction_type)
    VALUES (CURRENT_DATE, 1, 1, 10, 'BUY');
EXCEPTION WHEN OTHERS THEN
    ROLLBACK;
    RAISE EXCEPTION 'Erreur lors de l''insertion de la transaction. Transaction annulée.';
END;
COMMIT;