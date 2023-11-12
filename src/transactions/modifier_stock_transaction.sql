BEGIN;
    UPDATE stock_schema.transactions
    SET quantity_changed = 15
    WHERE transaction_id = 1;
COMMIT;
