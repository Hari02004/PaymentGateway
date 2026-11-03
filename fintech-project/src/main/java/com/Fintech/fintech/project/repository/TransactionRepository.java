package com.Fintech.fintech.project.repository;

import com.Fintech.fintech.project.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    long countByIpAddressAndTimestampAfter(String ipAddress, LocalDateTime dateAndTime);
}
