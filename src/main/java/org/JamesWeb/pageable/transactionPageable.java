package org.JamesWeb.pageable;

import org.JamesWeb.DTO.PaginationTransactionDetails;
import org.JamesWeb.DTO.TransactionDto;
import org.JamesWeb.mapper.TransactionMapper;
import org.JamesWeb.model.TransactionDetails;

import org.JamesWeb.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
@Component
public class transactionPageable {
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionDetailsRepository repository;
    public PaginationTransactionDetails<TransactionDto> getTransactions(String merchantId,Pageable pageable) {

        Page<TransactionDetails> page = repository.findByMerchantDetails_merchantId(merchantId,pageable);
        Page<TransactionDto> transactionDtos = page.map(transactionMapper::toDto);
        return PaginationTransactionDetails.from(transactionDtos);
    }
}
