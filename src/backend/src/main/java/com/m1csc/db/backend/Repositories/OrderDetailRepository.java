package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface OrderDetailRepository extends
        JpaRepository<OrderDetailEntity, BigInteger>,
        JpaSpecificationExecutor<OrderDetailEntity>
{
}
