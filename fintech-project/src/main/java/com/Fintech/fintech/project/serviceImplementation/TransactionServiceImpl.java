package com.Fintech.fintech.project.serviceImplementation;

import com.Fintech.fintech.project.dto.ResponseDto;
import com.Fintech.fintech.project.dto.TransactionDto;
import com.Fintech.fintech.project.entity.FraudDetection;
import com.Fintech.fintech.project.entity.FraudType;
import com.Fintech.fintech.project.entity.Transaction;
import com.Fintech.fintech.project.repository.TransactionRepository;
import com.Fintech.fintech.project.service.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



    @Service
    public class TransactionServiceImpl  implements TransactionServices{
        @Autowired
        private TransactionRepository transactionRepository;
        @Override
        public ResponseDto generateTransaction(TransactionDto dto) {

            Transaction transaction = new Transaction();
            if(dto == null){
                throw new RuntimeException("Transaction DTO cannot be null");
            }
            transaction.setTransactionId(generateTransactionId());
            transaction.setTransactionType(dto.getTransactionType());
            transaction.setAmount(dto.getAmount());
            transaction.setBeneficiaryAccount(dto.getReceiverAccount());
            transaction.setAccountNumber(dto.getSenderAccount());
            System.out.println(dto.getSenderAccount());
            transaction.setTimestamp(LocalDateTime.now());
            transaction.setIpAddress(dto.getIpAddress());
            double riskScore = 0;
            String reason = "Normal transaction";
            FraudType fraudType = null;
            String status = "CLEAR";
            FraudDetection fd = new FraudDetection();
            String sameAccountNumber = getDetailsOfSameAccount(dto.getSenderAccount(),dto.getReceiverAccount());
            String risk = getFraudDetails(dto.getAmount());
            if(!sameAccountNumber.equals("SAFE")) {
                fraudType = FraudType.SAME_ACCOUNT_NUMBER;
                riskScore += 60;
                reason = "Same Account number";
            }
            else if (risk.equals("High Risk")) {
                riskScore += 70;
                reason = "High amount transaction";
                fraudType = FraudType.HIGH_AMOUNT;
            } else if(risk.equals("Medium Risk")) {
                riskScore +=20;
                String riskDetails = checkIpAddress(transaction.getIpAddress(),transaction.getTimestamp().minusMinutes(5),2);
                if("RISK".equals(riskDetails)){
                    riskScore += 60;
                    reason = "Force fully sending request from same IP";
                    fraudType = FraudType.RAPID_TRANSACTION;
                }
            }
            else if (risk.equals("Low Risk")){
                riskScore += 0;
                String riskDetails = checkIpAddress(transaction.getIpAddress(),transaction.getTimestamp().minusMinutes(5),5);
                if("RISK".equals(riskDetails)){
                    riskScore += 60;
                    reason = "Force fully sending request from same IP";
                    fraudType = FraudType.RAPID_TRANSACTION;
                }
            }
            if(riskScore>=50){
                fd.setFraudFlag(true);
                fd.setFraudReason(reason);
                fd.setFraudType(fraudType);
                status = "FRAUD";
                fd.setRiskScore(riskScore);
                fd.setDetectedTime(LocalDateTime.now());
            }
            transaction.setStatus(status);
            fd.setTransaction(transaction);
            transaction.setFraudDetection(fd);
            transactionRepository.save(transaction);
            return mapToResponse(transaction);
        }
        public String getDetailsOfSameAccount(String senderAccountNumber, String receiverAccountNumber){
            if(senderAccountNumber.equals(receiverAccountNumber))
                return "SAME_ACCOUNT_NUMBER";
            return "SAFE";
        }

        public String getFraudDetails(double amount) {
            if (amount > 100000) {
                return "High Risk";
            } else if (amount > 50000) {
                return "Medium Risk";
            } else {
                return "Low Risk";
            }
        }


        public String generateTransactionId() {
            return "TXN" + System.currentTimeMillis();
        }
        public ResponseDto mapToResponse(Transaction transaction){
            ResponseDto responseDto = new ResponseDto();
            responseDto.setTransactionType(transaction.getTransactionType());
            responseDto.setAmount(transaction.getAmount());
            responseDto.setSenderAccount(transaction.getAccountNumber());
            responseDto.setReceiverAccount(transaction.getBeneficiaryAccount());
            responseDto.setStatus(transaction.getStatus());
            return  responseDto;
        }
        public String checkIpAddress(String ipAddress, LocalDateTime time,int limit){
            long count = transactionRepository.countByIpAddressAndTimestampAfter(ipAddress,time);
            if(count>limit){
                return "RISK";
            }
            return null;
        }
    }

