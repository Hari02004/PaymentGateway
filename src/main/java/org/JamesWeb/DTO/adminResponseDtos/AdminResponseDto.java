package org.JamesWeb.DTO.adminResponseDtos;

import lombok.Data;
import org.JamesWeb.DTO.PaginationTransactionDetails;

import java.math.BigDecimal;

@Data
public class AdminResponseDto<T,S> {
    Long totalMerchants;
    Long activeMerchants;
    Long totalPayments;
    Long successfulPayments;
    Long failedPayments;
    Long pendingPayments;
    Long todayTransactions;
    BigDecimal totalRevenue;
    BigDecimal todayRevenue;
    PaginationTransactionDetails<T> paginationTransactionDetails;
    PaginationTransactionDetails<S> adminsPaginationMerchantDetails;
}
