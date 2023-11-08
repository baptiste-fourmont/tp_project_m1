package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface SupplierRepository extends
        JpaRepository<SupplierEntity, BigInteger>,
        JpaSpecificationExecutor<SupplierEntity>
{
}
