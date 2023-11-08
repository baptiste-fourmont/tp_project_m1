package com.m1csc.db.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@Entity(name = "Supplier")
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "supplier_id")
    private BigInteger idSupplier;

    @Column (name = "supplier_name")
    private String name;

    @Column (name = "supplier_address")
    private String address;
}
