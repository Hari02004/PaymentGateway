package org.JamesWeb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@ToString(exclude = {"merchantDetails"})
public class    APIModel {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private MerchantDetails merchantDetails;

    @Column(name = "public_key", unique = true, nullable = false)
    private String publicKey;

    @Column(name = "secret_key_hash", unique = true, nullable = false)
    private String secretKeyHash;
    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
