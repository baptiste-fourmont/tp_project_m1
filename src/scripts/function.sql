CREATE OR REPLACE PROCEDURE logging_transaction()
LANGUAGE PLPGSQL AS $$
BEGIN
    INSERT INTO archived_transactions (transaction_id, date_transac, transaction_type)
    SELECT transaction_id, transaction_date, transaction_type
    FROM transactions
    WHERE transaction_date < CURRENT_DATE - INTERVAL '1 year';

    DELETE FROM transactions
    WHERE transaction_date < CURRENT_DATE - INTERVAL '1 year';
END $$;

-- Call the logging_transaction procedure
CALL logging_transaction();

-- Check the content of the archived_transactions table
SELECT * FROM archived_transactions;
