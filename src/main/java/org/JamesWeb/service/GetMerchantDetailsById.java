package org.JamesWeb.service;

import org.JamesWeb.exception.ApiUserEmailNotFoundException;
import org.JamesWeb.model.MerchantDetails;

import org.JamesWeb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class GetMerchantDetailsById {
    @Autowired
    private UserRepository userRepository;
    public MerchantDetails getMerchantDetailsById(Authentication authentication){
        String email = authentication.getName();
        MerchantDetails merchantDetails =  userRepository.findByEmail(email).orElseThrow(()->new ApiUserEmailNotFoundException("User is not found")).getMerchantDetails();
        if(merchantDetails==null){
            throw new ApiUserEmailNotFoundException("User doesn't enter the merchant details");
        }
        return merchantDetails;
    }
}
