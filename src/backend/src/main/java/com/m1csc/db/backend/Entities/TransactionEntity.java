package com.m1csc.db.backend.Entities;


import com.m1csc.db.backend.Entities.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


import java.sql.Date;

@Data
@Builder
@Entity(name = "Transaction")
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "transaction_id", columnDefinition="serial")
    private Long idTransaction;

    @Column(name = "transaction_date")
    private Date dateTransaction;

    @Column(name = "quantity_changed")
    private Integer quantityChanged;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "tr_type", name = "transaction_type")
    private TransactionType transactionType;

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
