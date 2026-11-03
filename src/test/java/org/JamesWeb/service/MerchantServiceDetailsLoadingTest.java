package org.JamesWeb.service;


import org.JamesWeb.exception.MerchantAlreadyExistException;
import org.JamesWeb.model.MerchantDetails;
import org.JamesWeb.model.users;
import org.JamesWeb.repository.MerchantRepository;
import org.JamesWeb.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MerchantServiceDetailsLoadingTest {

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MerchantServiceDetailsLoading service;

    @Test
    void shouldSaveMerchantSuccessfully() {

        MerchantDetails merchant = new MerchantDetails();
        merchant.setBusiness_email("merchant@gmail.com");
        merchant.setAddress("Chennai");
        merchant.setPh_number(Long.valueOf("9876543210"));

        users user = new users();
        user.setEmail("merchant@gmail.com");

        when(merchantRepository.findByBusiness_email("merchant@gmail.com"))
                .thenReturn(false);

        when(userRepository.findByEmail("merchant@gmail.com"))
                .thenReturn(Optional.of(user));

        when(merchantRepository.existsByMerchantId(any()))
                .thenReturn(false);

        when(merchantRepository.save(any(MerchantDetails.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        MerchantDetails result =
                service.saveMerchantDetails(
                        merchant,
                        "merchant@gmail.com"
                );
        assertNotNull(result);
        assertEquals("APPROVED", result.getStatus());
        assertEquals(user, result.getUser());
        assertNotNull(result.getMerchantId());
        verify(merchantRepository).save(any(MerchantDetails.class));
    }
    @Test
    void shouldReturnErrorAlreadyExistedMerchantDetails(){
        MerchantDetails merchantDetails = new MerchantDetails();
        merchantDetails.setBusiness_email("hk@gmail.com");
        when(merchantRepository.findByBusiness_email("hk@gmail.com")).thenReturn(true);
        assertThrows(MerchantAlreadyExistException.class,()->service.saveMerchantDetails(merchantDetails,"hk@gmail.com"));
        verify(merchantRepository,never()).save(any());
    }
    @Test
    void shouldThrowUsernameNotFoundException() {

        MerchantDetails merchant = new MerchantDetails();
        merchant.setBusiness_email("merchant@gmail.com");

        when(merchantRepository.findByBusiness_email("merchant@gmail.com"))
                .thenReturn(false);

        when(userRepository.findByEmail("merchant@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.saveMerchantDetails(
                        merchant,
                        "merchant@gmail.com"
                )
        );

        verify(merchantRepository, never()).save(any());
    }
}
