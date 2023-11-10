package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.CategoryEntity;
import com.m1csc.db.backend.Repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }
    
    public void createCategory(CategoryEntity category) {
        categoryRepository.save(category);
    }
    
    public void updateCategory(CategoryEntity category) {
        CategoryEntity _category = categoryRepository.findById(category.getIdCategory()).orElse(null);
        
        if (_category != null) {
            _category.setName(category.getName());
            categoryRepository.save(_category);
        }
        
        else
            throw new RuntimeException("Catégorie introuvable");
    }
    
    public void deleteCategory(CategoryEntity category) {
        categoryRepository.delete(category);
    }
}
