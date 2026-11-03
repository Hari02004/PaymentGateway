package com.Fintech.fintech.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private String accountNumber;
    private String beneficiaryAccount;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String status;
    private String ipAddress;
    private LocalDateTime timestamp;
    @OneToOne(mappedBy = "transaction",cascade = CascadeType.ALL)
    private FraudDetection fraudDetection;

}
