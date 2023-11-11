package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface OrderRepository extends
        JpaRepository<OrderEntity, Long>,
        JpaSpecificationExecutor<OrderEntity>
{
}
