package org.JamesWeb.repository;

import org.JamesWeb.model.MerchantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantDetails,String> {
    @Query(("SELECT COUNT(m) > 0 FROM MerchantDetails m WHERE m.business_email = :email"))
    boolean findByBusiness_email(@Param("email") String email);
    @Query(("SELECT COUNT(m) > 0 FROM MerchantDetails m WHERE m.merchantId = :merchant"))
    boolean existsByMerchantId(@Param("merchant") String merchant_id);
    @Query(("SELECT m FROM MerchantDetails m WHERE m.business_email = :email"))
    Optional<MerchantDetails> findByBusinessEmail(@Param("email") String email);
    @Query("SELECT m FROM MerchantDetails m WHERE m.merchantId = :merchantId")
    MerchantDetails findByMerchantId(@Param("merchantId") String merchantId);
    @Query("""
            SELECT m FROM MerchantDetails m 
            LEFT JOIN FETCH m.transactionDetails
            WHERE m.merchantId = :merchantId
                        """)
    MerchantDetails findByMerchantIdWithTransactions(@Param("merchantId") String merchantId);
    Long countByStatus(String status);

    @Query(" SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MerchantDetails m  where m.ph_number = :phNumber")
    boolean existsByPhNumber(@Param("phNumber") Long phNumber);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MerchantDetails m  where m.business_email = :businessEmail")
    boolean existsByBusinessEmail(@Param("businessEmail") String businessEmail);

    @Query(" SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END  FROM MerchantDetails m  where m.company_name = :companyName")
    boolean existsByCompanyName(@Param("companyName") String companyName);

}
