package com.Fintech.fintech.project.repository;

import com.Fintech.fintech.project.entity.FraudDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FraudDetectionRepository extends JpaRepository<FraudDetection, Long> {

    Optional<FraudDetection> findByTransactionId(Long transactionId);
}