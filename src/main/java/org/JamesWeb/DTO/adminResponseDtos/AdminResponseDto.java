package org.JamesWeb.DTO.adminResponseDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdminResponseDto {
    Long totalMerchants;
    Long activeMerchants;
    Long totalPayments;
    Long successfulPayments;
    Long failedPayments;
    Long pendingPayments;
    Long todayTransactions;
    BigDecimal totalRevenue;
    BigDecimal todayRevenue;
}
