package com.Fintech.fintech.project.service;

import com.Fintech.fintech.project.dto.AuthResponse;
import com.Fintech.fintech.project.dto.ResponseDto;
import com.Fintech.fintech.project.dto.TransactionDto;

public interface TransactionServices {
    public ResponseDto generateTransaction(TransactionDto dto);
}
