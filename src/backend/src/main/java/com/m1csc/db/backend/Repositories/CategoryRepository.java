package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigInteger;

public interface CategoryRepository extends
        JpaRepository<CategoryEntity, BigInteger>,
        JpaSpecificationExecutor<CategoryEntity> {

}
