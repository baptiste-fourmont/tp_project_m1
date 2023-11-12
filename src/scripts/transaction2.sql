BEGIN;

BEGIN
    UPDATE stock_schema.transactions
    SET quantity_changed = 15
    WHERE transaction_id = 1;
EXCEPTION WHEN OTHERS THEN
    -- En cas d'erreur, annuler la transaction
    ROLLBACK;
    RAISE EXCEPTION 'Erreur lors de la mise à jour de la transaction. Transaction annulée.';
END;

-- Valider la transaction si aucune erreur n'est survenue
COMMIT;