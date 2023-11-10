package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.TransactionEntity;
import com.m1csc.db.backend.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Transactional
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public List<TransactionEntity> getTransactions() {
        return transactionRepository.findAll();
    }
    public void createTransaction(TransactionEntity transaction) {
        transactionRepository.save(transaction);
    }

    public void updateTransaction(TransactionEntity transaction) {
        TransactionEntity _transaction = transactionRepository.findById(transaction.getIdTransaction()).orElse(null);

        if (_transaction != null) {
            _transaction.setDateTransaction(transaction.getDateTransaction());
            _transaction.setEmployee(transaction.getEmployee());
            _transaction.setProduct(transaction.getProduct());
            _transaction.setIdTransaction(transaction.getIdTransaction());
            _transaction.setTransactionType(transaction.getTransactionType());
            _transaction.setQuantityChanged(transaction.getQuantityChanged());
            transactionRepository.save(_transaction);
        }

        else
            throw new RuntimeException("Transaction introuvable");
    }

    public void deleteTransaction(TransactionEntity transaction) {
        transactionRepository.delete(transaction);
    }

    public void deleteTransactionById(BigInteger id) {
        transactionRepository.deleteById(id);
    }
}
