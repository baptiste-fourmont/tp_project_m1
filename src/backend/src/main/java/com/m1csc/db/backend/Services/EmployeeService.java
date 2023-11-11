package com.m1csc.db.backend.Services;

import com.m1csc.db.backend.Entities.EmployeeEntity;
import com.m1csc.db.backend.Entities.WarehouseEntity;
import com.m1csc.db.backend.Repositories.EmployeeRepository;
import com.m1csc.db.backend.Repositories.WarehouseRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<EmployeeEntity> getEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<EmployeeEntity> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public void createEmployee(EmployeeEntity employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(EmployeeEntity employee) {
        EmployeeEntity _employee = employeeRepository.findById(employee.getIdEmployee()).orElse(null);

        if (_employee != null) {
            _employee.setName(employee.getName());
            _employee.setDepartment(employee.getDepartment());
            _employee.setEmail(employee.getEmail());
            _employee.setWarehouse(employee.getWarehouse());
            employeeRepository.save(_employee);
        }

        else
            throw new RuntimeException("Employé introuvable");
    }

    public void deleteEmployee(EmployeeEntity employee) {
        employeeRepository.delete(employee);
    }

    public List<WarehouseEntity> getWarehouses(){
        return warehouseRepository.findAll();
    }
}
