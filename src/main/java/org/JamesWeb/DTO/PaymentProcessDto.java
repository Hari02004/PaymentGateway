package org.JamesWeb.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentProcessDto {
    BigDecimal amount;
    String txnId;
}
