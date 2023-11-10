package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.ProductWarehouseEntity;
import com.m1csc.db.backend.Repositories.ProductWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductWarehouseService {

    @Autowired
    private ProductWarehouseRepository productWarehouseRepository;

    

    public List<ProductWarehouseEntity> getProductWarehouses() {
        return productWarehouseRepository.findAll();
    }

    public Optional<ProductWarehouseEntity> getProductWarehouseById(BigInteger id) {
        return productWarehouseRepository.findById(id);
    }

    public void createProductWarehouse(ProductWarehouseEntity productWarehouse) {
        productWarehouseRepository.save(productWarehouse);
    }

    public void updateProductWarehouse(ProductWarehouseEntity productWarehouse) {
        ProductWarehouseEntity _productWarehouse = productWarehouseRepository.findById(productWarehouse.getIdProductWarehouse()).orElse(null);

        if (_productWarehouse != null) {
            _productWarehouse.setProduct(productWarehouse.getProduct());
            _productWarehouse.setWarehouse(productWarehouse.getWarehouse());
            _productWarehouse.setIdProductWarehouse(productWarehouse.getIdProductWarehouse());
            productWarehouseRepository.save(_productWarehouse);
        }

        else
            throw new RuntimeException("Produit introuvable");
    }

    public void deleteProductWarehouse(ProductWarehouseEntity productWarehouse) {
        productWarehouseRepository.delete(productWarehouse);
    }


}
