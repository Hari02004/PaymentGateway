package com.Fintech.fintech.project.security;

import com.Fintech.fintech.project.entity.User;
import com.Fintech.fintech.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {

        User user =
                userRepository
                        .findByUserName(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User Not Found"
                                )
                        );

        return org.springframework.security
                .core.userdetails.User
                .builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();
    }

}
