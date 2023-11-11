package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.OrderDetailEntity;
import com.m1csc.db.backend.Entities.OrderEntity;
import com.m1csc.db.backend.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface OrderDetailRepository extends
        JpaRepository<OrderDetailEntity, Long>,
        JpaSpecificationExecutor<OrderDetailEntity>
{

}
