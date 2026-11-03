package org.JamesWeb.service;
import org.JamesWeb.DTO.PaymentProcessDto;
import org.JamesWeb.exception.OrderIdExpired;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.model.TransactionStatus;
import org.JamesWeb.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostPaymentService {
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    public TransactionDetails postPayment(PaymentProcessDto paymentDetailsDto){
        TransactionDetails findTransactionDetails = transactionDetailsRepository.findByTransactionId(paymentDetailsDto.getTxnId()).
                orElseThrow(()-> new UsernameNotFoundException("Transaction id not found"));
        if(!findTransactionDetails.getStatus().equals(TransactionStatus.WAITING)){
            throw new OrderIdExpired("Transaction is already processed or expired");
        }
        findTransactionDetails.setStatus(TransactionStatus.APPROVED);
        transactionDetailsRepository.save(findTransactionDetails);
        return findTransactionDetails;
    }

}
