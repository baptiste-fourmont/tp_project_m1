package com.m1csc.db.backend.Repositories;


import com.m1csc.db.backend.Entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface TransactionRepository extends
        JpaRepository<TransactionEntity, Long>,
        JpaSpecificationExecutor<TransactionEntity>
{
}
