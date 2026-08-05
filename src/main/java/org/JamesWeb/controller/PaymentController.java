package org.JamesWeb.controller;

import jakarta.annotation.Nonnull;
import org.JamesWeb.DTO.PaymentProcessDto;
import org.JamesWeb.exception.OrderIdExpired;
import org.JamesWeb.exception.UserEmailNotFoundException;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.model.TransactionStatus;
import org.JamesWeb.repository.TransactionDetailsRepository;
import org.JamesWeb.service.PostPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Slf4j
@Controller
public class PaymentController {
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Autowired
    private PostPaymentService postPaymentService;
    @GetMapping("processPayment/{transactionId}")
    public String handlePayment(@PathVariable("transactionId") String transactionId, @Nonnull Model model){
            log.info("GET processPayment/{} - preparing payment page", transactionId);
            TransactionDetails findTransactionDetails = transactionDetailsRepository.findByTransactionId(transactionId).
                    orElseThrow(()-> new OrderIdExpired("Transaction id not found"));
            model.addAttribute("txnId",transactionId);
            model.addAttribute("amount",findTransactionDetails.getAmount());
            return "payment-page";
        }
    @PostMapping("/processPayment")
    public String handlePostPayment(@ModelAttribute PaymentProcessDto paymentProcessDto, @Nonnull Model model){
            log.info("POST /processPayment - handling post payment");
            TransactionDetails findTransactionDetails = postPaymentService.postPayment(paymentProcessDto);
            model.addAttribute("transactionDetails",findTransactionDetails);
            return "paymentSuccessFull";
        }
}
