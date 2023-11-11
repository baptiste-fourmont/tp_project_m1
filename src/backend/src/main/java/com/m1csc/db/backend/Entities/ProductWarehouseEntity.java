package com.m1csc.db.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@Entity(name = "ProductWarehouse")
@Table(name = "product_warehouse")
@NoArgsConstructor
@AllArgsConstructor
public class ProductWarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "product_warehouse_id", columnDefinition="serial")
    private Long idProductWarehouse;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;
}
