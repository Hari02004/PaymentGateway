package org.JamesWeb.service;

import org.JamesWeb.model.APIModel;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.repository.APIRepo;
import org.JamesWeb.util.ApiKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class APIService {
    @Autowired
    private APIRepo apiRepo;
    @Autowired
    private GetMerchantDetailsById getMerchantDetailsById;
    public List<APIModel> apiService(Authentication authentication) throws Exception {
        String publicKey = ApiKeyUtil.generatePublicKey();
        String secretKey = ApiKeyUtil.generateSecretKey();
        MerchantDetails merchantDetails = getMerchantDetailsById.getMerchantDetailsById(authentication);
        if(merchantDetails.getApiModel().isEmpty() && merchantDetails.getStatus().equals("Approved")){
            APIModel apiModel = new APIModel();
            apiModel.setPublicKey(publicKey);
            apiModel.setActive(true);
            apiModel.setMerchantDetails(merchantDetails);
            String encryptSecretKey = ApiKeyUtil.encrypt(secretKey);
            apiModel.setSecretKeyHash(encryptSecretKey);
            apiModel.setCreatedAt(LocalDateTime.now());
            apiRepo.save(apiModel);
            return List.of(apiModel);
        }
        return merchantDetails.getApiModel();
    }
}
