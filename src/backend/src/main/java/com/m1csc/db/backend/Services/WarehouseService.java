package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.WarehouseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m1csc.db.backend.Repositories.WarehouseRepository;

import java.util.List;

@Service
@Transactional

public class WarehouseService {
    
    @Autowired
    private WarehouseRepository warehouseRepository;
    
    public List<WarehouseEntity> getWarehouses() {
        return warehouseRepository.findAll();
    }
    
    public void createWarehouse(WarehouseEntity warehouse) {
        warehouseRepository.save(warehouse);
    }
    
    public void updateWarehouse(WarehouseEntity warehouse) {
        WarehouseEntity _warehouse = warehouseRepository.findById(warehouse.getIdWarehouse()).orElse(null);
        
        if (_warehouse != null) {
            _warehouse.setName(warehouse.getName());
            _warehouse.setLocation(warehouse.getLocation());
            warehouseRepository.save(_warehouse);
        }
        
        else
            throw new RuntimeException("Entrepôt introuvable");
    }
    
    public void deleteWarehouse(WarehouseEntity warehouse) {
        warehouseRepository.delete(warehouse);
    }
}
