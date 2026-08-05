package org.JamesWeb.controller;

import org.JamesWeb.model.APIModel;
import org.JamesWeb.service.APIService;
import org.JamesWeb.util.ApiKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class APIController {
    @Autowired
    private APIService apiService;
    @GetMapping("/merchant/apikey")
    public String apiConfig(){
            log.info("GET /merchant/apikey - showing API config page");
            return "Api";
        }
    @GetMapping("/merchant/apikey/getDetails")
    public String apiCont(Authentication authentication, Model model) throws Exception {
                log.info("GET /merchant/apikey/getDetails - fetching API details for {}", authentication != null ? authentication.getName() : "anonymous");
                List<APIModel> apiModel = apiService.apiService(authentication);
                String secretKey = apiModel.getFirst().getSecretKeyHash();
                String decryptKey = ApiKeyUtil.decrypt(secretKey);
                model.addAttribute("secretKey",decryptKey);
                model.addAttribute("apiDetails",apiModel);
            return "Api";
        }
}
