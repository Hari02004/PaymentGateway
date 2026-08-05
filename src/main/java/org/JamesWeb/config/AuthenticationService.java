package org.JamesWeb.config;

import jakarta.servlet.http.HttpServletRequest;
import org.JamesWeb.exception.ApiExceptionHandler;
import org.JamesWeb.model.APIModel;
import org.JamesWeb.repository.APIRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {
    @Autowired
    private APIRepo apiRepo;
    public boolean request(HttpServletRequest request){
        String publicKey = request.getHeader("Public-Key");
        APIModel apiModel = apiRepo.findByPublicKey(publicKey).orElseThrow(
                ()->new ApiExceptionHandler("User name is not present in the API model"));
        return apiModel.getPublicKey().equals(publicKey);
    }
}
