package org.JamesWeb.repository;

import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.TransactionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long> {
    Optional<TransactionDetails> findByTransactionId(String transactionId);
    Optional<TransactionDetails> findByMerchantDetailsAndOrderId(MerchantDetails merchantDetails, String orderId);
    Optional<TransactionDetails> findByOrderId(String orderId);
    @Query("Select m from TransactionDetails m where m.merchantDetails.merchantId = :merchantId")
    Page<TransactionDetails> findByMerchantDetails_merchantId(@Param("merchantId") String merchantId, Pageable pageable);
}
