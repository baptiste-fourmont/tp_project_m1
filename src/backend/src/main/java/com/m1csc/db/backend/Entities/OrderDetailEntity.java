package com.m1csc.db.backend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@Entity(name = "OrderDetail")
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "order_detail_id", columnDefinition="serial")
    private Long idOrderDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
