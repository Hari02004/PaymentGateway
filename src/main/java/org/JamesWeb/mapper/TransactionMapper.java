package org.JamesWeb.mapper;

import org.JamesWeb.DTO.TransactionDto;
import org.JamesWeb.model.TransactionDetails;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import java.util.List;

@Component
public class TransactionMapper {

    public TransactionDto toDto(TransactionDetails transactionDetails) {
        if (transactionDetails == null) return null;
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(transactionDetails.getTransactionId());
        dto.setOrderId(transactionDetails.getOrderId());
        dto.setMerchantId(transactionDetails.getMerchantDetails() != null ? transactionDetails.getMerchantDetails().getMerchantId() : null);
        dto.setBusinessEmail(transactionDetails.getBusinessEmail());
        dto.setCustomerName(transactionDetails.getCustomerName());
        dto.setAmount(transactionDetails.getAmount());
        dto.setCurrency(transactionDetails.getCurrency());
        dto.setStatus(transactionDetails.getStatus());
        dto.setFailureReason(transactionDetails.getFailureReason());
        dto.setClientIp(transactionDetails.getClientIp());
        return dto;
    }

    public List<TransactionDto> toDtoList(List<TransactionDetails> transactionDetailsList) {
        if (transactionDetailsList == null) return null;
        return transactionDetailsList.stream().map(this::toDto).collect(Collectors.toList());
    }
}

