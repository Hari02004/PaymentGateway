package org.JamesWeb.repository;

import org.JamesWeb.model.APIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface APIRepo extends JpaRepository<APIModel,Long> {
    @Query("SELECT m FROM APIModel m WHERE m.merchantDetails.merchantId = :merchantId")
    Optional<APIModel> findByMerchantDetails(@Param("merchantId") String merchantId);
    @Query(("SELECT m FROM APIModel m WHERE m.publicKey = :publicKey"))
    Optional<APIModel> findByPublicKey(@Param("publicKey") String publicKey);
/*//    @Query("SELECT m FROM APIModel m where m.merchantDetails = :merchantDetails")
//    Optional<APIModel> findByMerchantDetailObject(@Param("merchantDetails") String merchantDetails);*/
}
