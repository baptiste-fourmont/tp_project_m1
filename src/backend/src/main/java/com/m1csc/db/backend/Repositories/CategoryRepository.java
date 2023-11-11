package com.m1csc.db.backend.Repositories;

import com.m1csc.db.backend.Entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface CategoryRepository extends
        JpaRepository<CategoryEntity, Long>,
        JpaSpecificationExecutor<CategoryEntity> {

}
