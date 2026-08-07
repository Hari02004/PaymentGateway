    package org.JamesWeb.service;

    import org.JamesWeb.exception.MerchantAlreadyExistException;
    import org.JamesWeb.model.MerchantDetails;
    import org.JamesWeb.model.users;
    import org.JamesWeb.repository.MerchantRepository;
    import org.JamesWeb.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;
    import java.time.LocalDateTime;

    @Service
    public class MerchantServiceDetailsLoading {
        @Autowired
        private MerchantRepository merchantRepository;
        @Autowired
        private UserRepository userRepository;
        public MerchantDetails saveMerchantDetails(MerchantDetails merchantDetails,String loginWithEmail){
             if(merchantRepository.findByBusiness_email(merchantDetails.getBusiness_email())){
                 throw new MerchantAlreadyExistException("Merchant Email already present");
             }
             users userDetail = userRepository.findByEmail(loginWithEmail).orElseThrow(()->new UsernameNotFoundException("User doesn't found"));
             String merchantID = "MER" +(1000 + (int) (Math.random() * 9000));
             if(merchantRepository.existsByMerchantId(merchantID)){
                 merchantID = merchantID+1;
             }
             String status = "Waiting";
             merchantDetails.setMerchantId(merchantID);
             merchantDetails.setStatus(status);
             merchantDetails.setCreated_at(LocalDateTime.now());
             merchantDetails.setUser(userDetail);
             merchantRepository.save(merchantDetails);
            return merchantDetails;
        }
        public void updateMerchant(MerchantDetails merchantDetails){
            MerchantDetails merchantDetails1 = merchantRepository.findByMerchantId(merchantDetails.getMerchantId());
            merchantDetails1.setBusiness_email(merchantDetails.getBusiness_email());
            merchantDetails1.setAddress(merchantDetails.getAddress());
            merchantDetails1.setPh_number(merchantDetails.getPh_number());
            merchantRepository.save(merchantDetails1);
        }
    }
