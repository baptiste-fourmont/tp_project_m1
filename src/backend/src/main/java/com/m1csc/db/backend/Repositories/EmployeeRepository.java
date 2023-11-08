package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface EmployeeRepository extends
        JpaRepository<EmployeeEntity, BigInteger>,
        JpaSpecificationExecutor<EmployeeEntity>
{
}
