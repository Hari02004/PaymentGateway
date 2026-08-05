package org.JamesWeb.controller;

import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.repository.MerchantRepository;
import org.JamesWeb.service.GetMerchantDetailsById;
import org.JamesWeb.service.MerchantServiceDetailsLoading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
public class MerchantDetailsController {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private GetMerchantDetailsById getMerchantDetailsById;
    @Autowired
    private MerchantServiceDetailsLoading merchantServiceDetailsLoading;
    @GetMapping("/merchant")
    public String merchant(){
        return "merchantdetails";
    }
    @GetMapping("/merchant/fetch/details")
    public String merchant1(Authentication authentication,Model model){
            log.info("GET /merchant/fetch/details - fetching merchant details for {}", authentication != null ? authentication.getName() : "anonymous");
            MerchantDetails merchantDetails = getMerchantDetailsById.getMerchantDetailsById(authentication);
            model.addAttribute("MerchantDetails",merchantDetails);
            return "SuccessFull";
        }
    @PostMapping("/merchant")
    public String merchantDetails(@ModelAttribute MerchantDetails merchantDetails, Principal principal, Model model){
            String loginByEmail = principal.getName();
                log.info("POST /merchant - saving merchant details for {}", loginByEmail);
                MerchantDetails merchantDetails1 = merchantServiceDetailsLoading.saveMerchantDetails(merchantDetails, loginByEmail);
                model.addAttribute("message","Merchant details successfully");
                model.addAttribute("MerchantDetails",merchantDetails1);
                return "SuccessFull";

        }
    @GetMapping("/merchant/fetch/edit/{merchantId}")
    public String editMerchant(@PathVariable("merchantId") String merchantId, Model model) {
            log.info("GET /merchant/fetch/edit/{} - editing merchant", merchantId);
        MerchantDetails merchant = merchantRepository.findByMerchantId(merchantId);

        model.addAttribute("merchant", merchant);

        return "editMerchant";
    }
    @PostMapping("/merchant/update")
    public String updateMerchant(@ModelAttribute MerchantDetails merchantDetails){
            log.info("POST /merchant/update - updating merchant {}", merchantDetails.getMerchantId());
            merchantServiceDetailsLoading.updateMerchant(merchantDetails);
            return "redirect:/merchant/fetch/details";
        }
}
