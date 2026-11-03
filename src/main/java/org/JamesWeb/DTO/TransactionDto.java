package org.JamesWeb.DTO;

import lombok.Data;
import org.JamesWeb.model.TransactionStatus;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private String transactionId;
    private String orderId;
    private String merchantId;
    private String businessEmail;
    private String customerName;
    private BigDecimal amount;
    private String currency;
    private TransactionStatus status;
    private String failureReason;
    private String clientIp;
}
