package com.Fintech.fintech.project.dto;


import com.Fintech.fintech.project.entity.TransactionType;
import lombok.Data;
@Data
public class TransactionDto {
    private double amount;
    private TransactionType transactionType;
    private String senderAccount;
    private String receiverAccount;
    private String ipAddress;
}
