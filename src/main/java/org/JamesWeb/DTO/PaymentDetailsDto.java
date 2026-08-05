package org.JamesWeb.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PaymentDetailsDto {
    @NotNull(message = "OrderId can't be null")
    private String orderId;
    @NotNull(message = "Amount can't be null")
    private BigDecimal amount;
    private String currency;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is registered")
    private String businessEmail;
    @NotNull(message = "CustomerName can't be null")
    private String customerName;

    @Override
    public String toString() {
        return "orderId=" + orderId + '|' +
                "customerName=" + customerName + '|' +
                "businessEmail=" + businessEmail + '|' +
                "amount=" + amount + '|' +
                "currency=" + currency + '|';
    }
}
