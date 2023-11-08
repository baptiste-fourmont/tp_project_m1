package com.m1csc.db.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@Entity(name = "Product")
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "product_id")
    private BigInteger idProduct;

    @Column(name = "product_name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @OneToOne
    @JoinColumn(name = "categorie_id")
    private CategoryEntity category;

    @OneToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;
}
