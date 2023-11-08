package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface ProductRepository extends
        JpaRepository<ProductEntity, BigInteger>,
        JpaSpecificationExecutor<ProductEntity>

{
}
