package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface WarehouseRepository extends
        JpaRepository<WarehouseEntity, Long>,
        JpaSpecificationExecutor<WarehouseEntity> {
}
