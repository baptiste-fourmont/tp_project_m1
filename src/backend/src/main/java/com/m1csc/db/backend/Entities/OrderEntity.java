package com.m1csc.db.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Date;

@Data
@Builder
@Entity(name = "Order")
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "order_id")
    private BigInteger idOrder;

    @Column (name = "order_date")
    private Date dateOrder;

    @OneToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;
}