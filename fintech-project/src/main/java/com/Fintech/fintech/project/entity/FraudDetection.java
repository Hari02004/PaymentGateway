package com.Fintech.fintech.project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FraudDetection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean fraudFlag;
    private String fraudReason;
    @Enumerated(EnumType.STRING)
    private FraudType fraudType;
    private double riskScore;
    private LocalDateTime detectedTime;
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
