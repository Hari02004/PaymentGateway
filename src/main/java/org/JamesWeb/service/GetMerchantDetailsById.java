package org.JamesWeb.service;

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
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User is not found")).getMerchantDetails();
    }
}
