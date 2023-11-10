package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Repositories.ProductRepository;
import com.m1csc.db.backend.Entities.ProductEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }
    
    public void createProduct(ProductEntity product) {
        productRepository.save(product);
    }
    
    public void updateProduct(ProductEntity product) {
        ProductEntity _product = productRepository.findById(product.getIdProduct()).orElse(null);
        
        if (_product != null) {
            _product.setName(product.getName());
            _product.setQuantity(product.getQuantity());
            _product.setPrice(product.getPrice());
            _product.setCategory(product.getCategory());
            _product.setSupplier(product.getSupplier());
            productRepository.save(_product);
        }
        
        else
            throw new RuntimeException("Produit introuvable");
    }
    
    public void deleteProduct(ProductEntity product) {
        productRepository.delete(product);
    }
}
