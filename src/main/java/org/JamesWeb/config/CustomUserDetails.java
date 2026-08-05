package org.JamesWeb.config;

import org.JamesWeb.model.User_roles;
import org.JamesWeb.model.users;
import org.JamesWeb.repository.UserRepository;
import org.JamesWeb.repository.UserRolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CustomUserDetails implements UserDetailsService {
    private final UserRepository loadUserDetails;
    public CustomUserDetails(UserRepository loadUserDetails){
        this.loadUserDetails = loadUserDetails;
    }
    @Autowired
    public UserRolesRepo userRolesRepo;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        users user = loadUserDetails.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        
        // Get roles by user ID
        java.util.List<org.JamesWeb.model.User_roles> userRoles = userRolesRepo.findByUsersId(user.getId());

        List<GrantedAuthority> authorities = userRoles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(
                        role.getRole().getRole().name()))
                .toList();
        return  org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
