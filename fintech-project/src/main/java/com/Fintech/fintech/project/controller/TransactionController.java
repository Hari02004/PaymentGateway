package com.Fintech.fintech.project.controller;

import com.Fintech.fintech.project.dto.ResponseDto;
import com.Fintech.fintech.project.dto.TransactionDto;
import com.Fintech.fintech.project.service.TransactionServices;
import com.Fintech.fintech.project.serviceImplementation.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionServices transactionService;
    @PostMapping
    public ResponseDto transactionController(@RequestBody TransactionDto dto){
        return transactionService.generateTransaction(dto);
    }
}
