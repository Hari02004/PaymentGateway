package com.Fintech.fintech.project.dto;

import com.Fintech.fintech.project.entity.TransactionType;
import lombok.Data;

@Data
public class ResponseDto {
     private double amount;
     private TransactionType transactionType;
     private String SenderAccount;
     private String receiverAccount;
     private String status;
}
