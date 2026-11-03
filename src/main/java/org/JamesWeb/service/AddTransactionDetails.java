package org.JamesWeb.service;

import org.JamesWeb.DTO.PaymentDetailsDto;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.model.TransactionStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class AddTransactionDetails {
    public TransactionDetails addDetails(PaymentDetailsDto paymentDetailsDto,MerchantDetails merchantDetails){
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setOrderId(paymentDetailsDto.getOrderId());
        transactionDetails.setAmount(paymentDetailsDto.getAmount());
        transactionDetails.setCurrency(paymentDetailsDto.getCurrency());
        transactionDetails.setBusinessEmail(paymentDetailsDto.getBusinessEmail());
        transactionDetails.setCustomerName(paymentDetailsDto.getCustomerName());
        transactionDetails.setMerchantDetails(merchantDetails);
        String transactionId = "TXN"+merchantDetails.getMerchantId()+ System.currentTimeMillis();
        transactionDetails.setTransactionId(transactionId);
        transactionDetails.setCreatedAt(LocalDateTime.now());
        transactionDetails.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        transactionDetails.setStatus(TransactionStatus.WAITING);
        return transactionDetails;
    }
}
