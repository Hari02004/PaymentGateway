package org.JamesWeb.service;

import jakarta.servlet.http.HttpServletRequest;
import org.JamesWeb.DTO.PaymentDetailsDto;
import org.JamesWeb.DTO.ResponseDto;
import org.JamesWeb.exception.AuthenticationPasswordException;
import org.JamesWeb.exception.OrderIdExpired;
import org.JamesWeb.exception.UserEmailNotFoundException;
import org.JamesWeb.model.APIModel;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.TransactionDetails;
import org.JamesWeb.model.TransactionStatus;
import org.JamesWeb.repository.APIRepo;
import org.JamesWeb.repository.MerchantRepository;
import org.JamesWeb.repository.TransactionDetailsRepository;
import org.JamesWeb.util.ApiKeyUtil;
import org.JamesWeb.util.generateUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentProcess {
    @Autowired
    private TransactionDetailsRepository detailsRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private APIRepo apiRepo;
    @Autowired
    private AddTransactionDetails addTransactionDetails;
    @Autowired
    private generateUrl generateUrl;
    public ResponseDto paymentProcesser(HttpServletRequest servletRequest, PaymentDetailsDto paymentDto)  {
        if(servletRequest.getHeader("Authorization") == null){
            throw new AuthenticationPasswordException("There is no authorization key");
        }
        APIModel apiModel1 = apiRepo.findByPublicKey(servletRequest.getHeader("Public-Key")).orElseThrow(()-> new AuthenticationPasswordException("Authentication mismatch"));
        String merchantId = apiModel1.getMerchantDetails().getMerchantId();
        MerchantDetails merchantDetails = merchantRepository.findByMerchantId(merchantId);
        if(!merchantDetails.getBusiness_email().equals(paymentDto.getBusinessEmail())){
            throw new UserEmailNotFoundException("Please enter your business email address");
        }
        Optional<TransactionDetails> orderIdIsPresent = detailsRepository.findByOrderId(paymentDto.getOrderId());
            if(orderIdIsPresent.isPresent()){
                if(orderIdIsPresent.get().getStatus().equals(TransactionStatus.APPROVED)){
                    return convertToResponse(orderIdIsPresent.get(),null);
                }
            }
            String url;
            APIModel apiModel = apiRepo.findByMerchantDetails(merchantDetails.getMerchantId()).
                    orElseThrow(()->new UserEmailNotFoundException("Invalid validation"));
            String splitAuth = servletRequest.getHeader("Authorization").substring(7);
            String decryptedKey = ApiKeyUtil.decrypt(apiModel.getSecretKeyHash());
            String checkAuth = ApiKeyUtil.hashSecretKey(paymentDto.toString(),decryptedKey);
            Optional<TransactionDetails> transaction =
                detailsRepository.findByMerchantDetailsAndOrderId(merchantDetails, paymentDto.getOrderId());
            if(!checkAuth.equals(splitAuth)){
                throw new AuthenticationPasswordException("AuthenticationMismatch");
            }
            if (transaction.isPresent()) {

                TransactionDetails details = transaction.get();

                if (details.getStatus() == TransactionStatus.WAITING) {

                    if (LocalDateTime.now().isAfter(details.getExpiresAt())) {

                        details.setStatus(TransactionStatus.FAILED);
                        detailsRepository.save(details);

                    throw new OrderIdExpired("Transaction has expired");
                }

                url = generateUrl.createUrl() + details.getTransactionId();
                return convertToResponse(details, url);
            }
        }
            TransactionDetails transactionDetails = addTransactionDetails.addDetails(paymentDto,merchantDetails);
            transactionDetails.setClientIp(servletRequest.getRemoteAddr());
            url = generateUrl.createUrl()+transactionDetails.getTransactionId();
            detailsRepository.save(transactionDetails);

        return convertToResponse(transactionDetails,url);
    }
    public ResponseDto convertToResponse(TransactionDetails details,String url){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCurrency(details.getCurrency());
        responseDto.setAmount(details.getAmount());
        responseDto.setCustomerName(details.getCustomerName());
        responseDto.setStatus(details.getStatus());
        responseDto.setTransactionId(details.getTransactionId());
        responseDto.setBusinessEmail(details.getBusinessEmail());
        responseDto.setMerchantId(details.getMerchantDetails().getMerchantId());
        responseDto.setUrl(url);
        return responseDto;
    }

}
