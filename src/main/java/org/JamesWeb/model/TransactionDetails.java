package org.JamesWeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(unique = true,nullable = false)
    private String orderId;

    @ManyToOne
    @JoinColumn(name="merchant_id",nullable = false)
    private MerchantDetails merchantDetails;

    @Column(nullable = false)
    private String businessEmail;

    private String customerName;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)@Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String failureReason;

    private String clientIp;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

}
