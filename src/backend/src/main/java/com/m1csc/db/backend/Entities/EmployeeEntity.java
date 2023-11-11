package com.m1csc.db.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@Entity(name = "Employee")
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "employee_id", columnDefinition="serial")
    private Long idEmployee;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "department")
    private Integer department;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;
}
