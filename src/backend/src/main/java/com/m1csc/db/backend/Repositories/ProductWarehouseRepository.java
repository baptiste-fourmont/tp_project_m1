package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.ProductWarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface ProductWarehouseRepository extends
        JpaRepository<ProductWarehouseEntity, Long>,
        JpaSpecificationExecutor<ProductWarehouseEntity>
{
}
