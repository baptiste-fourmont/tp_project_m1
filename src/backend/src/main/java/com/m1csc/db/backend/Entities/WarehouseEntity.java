package com.m1csc.db.backend.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@Entity(name = "Warehouse")
@Table(name = "warehouses")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "warehouse_id", columnDefinition="serial")
    private Long idWarehouse;

    @Column(name = "warehouse_name")
    private String name;

    @Column(name = "location")
    private String location;
}
