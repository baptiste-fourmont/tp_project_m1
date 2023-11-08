package com.m1csc.db.backend.Repositories;


import com.m1csc.db.backend.Entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface TransactionRepository extends
        JpaRepository<TransactionEntity, BigInteger>,
        JpaSpecificationExecutor<TransactionEntity>
{
}
