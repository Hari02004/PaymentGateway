package org.JamesWeb.service;

import jakarta.servlet.http.HttpServletRequest;
import org.JamesWeb.DTO.PaginationTransactionDetails;
import org.JamesWeb.DTO.TransactionDto;
import org.JamesWeb.exception.ApiExceptionHandler;
import org.JamesWeb.exception.AuthenticationPasswordException;
import org.JamesWeb.mapper.TransactionMapper;
import org.JamesWeb.model.APIModel;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.pageable.transactionPageable;
import org.JamesWeb.repository.APIRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionDetailsForMerchant {
    @Autowired
    private APIRepo apiRepo;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private transactionPageable pageable1;
    public PaginationTransactionDetails<TransactionDto> getTransactionDetailsOfMerchant(HttpServletRequest servletRequest, int page, int size){
        String auth = servletRequest.getHeader("Authorization");
        if(auth==null){
            throw new AuthenticationPasswordException("Authentication_Mismatch");
        }
        String publicKey = servletRequest.getHeader("Public-Key");
        APIModel apiModel = apiRepo.findByPublicKey(publicKey).orElseThrow(()->
                new ApiExceptionHandler("Public key not found"));

        MerchantDetails merchantDetail = apiModel.getMerchantDetails();
        String merchantId = merchantDetail.getMerchantId();
        Pageable pageable = PageRequest.of(page,size);

        return pageable1.getTransactions(merchantId,pageable);
    }

}
