package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.SupplierEntity;
import com.m1csc.db.backend.Repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierEntity> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<SupplierEntity> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public void createSupplier(SupplierEntity supplier) {
        supplierRepository.save(supplier);
    }

    public void updateSupplier(SupplierEntity supplier) {
        SupplierEntity _supplier = supplierRepository.findById(supplier.getIdSupplier()).orElse(null);

        if (_supplier != null) {
            _supplier.setName(supplier.getName());
            _supplier.setIdSupplier(supplier.getIdSupplier());
            supplierRepository.save(_supplier);
        }

        else
            throw new RuntimeException("Fournisseur introuvable");
    }

    public void deleteSupplier(SupplierEntity supplier) {
        supplierRepository.delete(supplier);
    }



}
