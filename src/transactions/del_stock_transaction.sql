\c stock_management

BEGIN;
    DELETE FROM stock_schema.transactions
    WHERE transaction_id = 2;
    ROLLBACK;
COMMIT;
