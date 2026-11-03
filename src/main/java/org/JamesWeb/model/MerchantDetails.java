package org.JamesWeb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"user", "apiModel","transactionDetails"})
public class MerchantDetails {
    @Id
    @Column(unique = true,name = "merchant_id")
    private String merchantId;
    @Column(unique = true,nullable = false)
    private String company_name;
    @Column(nullable = false)
    private String owner_name;
    @Column(unique = true,nullable = false)
    private String business_email;
    @Column(unique = true,nullable = false)
    private Long ph_number;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDateTime created_at;
    @OneToOne
    @JoinColumn(name = "user_id")
    private users user;
    @OneToMany(mappedBy = "merchantDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<APIModel> apiModel;
    @OneToMany(mappedBy = "merchantDetails",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionDetails> transactionDetails;
}
