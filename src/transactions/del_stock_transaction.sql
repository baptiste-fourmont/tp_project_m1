-- Démarrer une transaction
BEGIN;

-- Tenter de supprimer une transaction
BEGIN
    DELETE FROM stock_schema.transactions
    WHERE transaction_id = 1;
EXCEPTION WHEN OTHERS THEN
    -- En cas d'erreur, annuler la transaction
    ROLLBACK;
    RAISE EXCEPTION 'Erreur lors de la suppression de la transaction. Transaction annulée.';
END;

-- Valider la transaction si aucune erreur n'est survenue
COMMIT;