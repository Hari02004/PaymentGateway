package org.JamesWeb.controller;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import org.JamesWeb.DTO.*;
import org.JamesWeb.service.PaymentProcess;
import org.JamesWeb.service.TransactionDetailsForMerchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
public class MerchantController {
    @Autowired
    private PaymentProcess paymentProcess;
    @Autowired
    private TransactionDetailsForMerchant transactionDetailsForMerchant;
    @PostMapping(value = "/payment",
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> paymentHandler(@Nonnull HttpServletRequest servletRequest,
                                            @RequestBody PaymentDetailsDto paymentDto)  {
            log.info("POST /payment - paymentHandler called");
            ResponseDto successResponse = paymentProcess.paymentProcesser(servletRequest,paymentDto);
            ApiResponse<ResponseDto> apiResponse = new ApiResponse<>(true,"Payment API is successFull",successResponse);
            return ResponseEntity.ok(apiResponse);
        }
    @GetMapping("/merchant/transaction")
    public ResponseEntity<?> transactionHandler(@Nonnull HttpServletRequest servletRequest,@RequestParam(name="page") int page,
                                                @RequestParam(name="size") int size){
        PaginationTransactionDetails<TransactionDto> transactionDetails = transactionDetailsForMerchant.getTransactionDetailsOfMerchant(servletRequest,page,size);
        ApiResponse<PaginationTransactionDetails<TransactionDto>> apiResponse = new ApiResponse<>(true,"Payment gateway successFull",transactionDetails);
        log.info("Transaction response: {}", apiResponse.getData());
        return ResponseEntity.ok(apiResponse);
    }
}
