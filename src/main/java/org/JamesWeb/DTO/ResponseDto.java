package org.JamesWeb.DTO;

import lombok.Data;
import org.JamesWeb.model.TransactionStatus;
import java.math.BigDecimal;

@Data
public class ResponseDto {
    private String url;
    private String customerName;
    private String businessEmail;
    private BigDecimal amount;
    private TransactionStatus status;
    private String currency;
    private String transactionId;
    private String merchantId;
}
