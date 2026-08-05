package org.JamesWeb.service;

import org.JamesWeb.DTO.LoginRequestDto;
import org.JamesWeb.exception.AdminErrorException;
import org.JamesWeb.exception.UserEmailNotFoundException;
import org.JamesWeb.model.Role;
import org.JamesWeb.model.User_roles;
import org.JamesWeb.model.users;
import org.JamesWeb.repository.UserRepository;
import org.JamesWeb.repository.UserRolesRepo;
import org.JamesWeb.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesRepo userRolesRepo;
    @Autowired
    private JwtService jwtService;
    
    public String login(LoginRequestDto requestDto){
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword());
            Authentication authenticated = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            users user = userRepository.findByEmail(requestDto.getUsername()).
                    orElseThrow(
                            ()->new UserEmailNotFoundException("Email not found"));

            List<User_roles> userRoles = userRolesRepo.findByUsersId(user.getId());
            userRoles.forEach(ur -> System.out.println("Role: " + ur.getRole().getRole()));
            boolean isAdmin = userRoles.stream()
                    .anyMatch(userRole ->
                            userRole.getRole().getRole().equals(Role.ROLE_ADMIN));
            if(isAdmin){
                return jwtService.generateToken(requestDto.getUsername());
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
       throw new AdminErrorException("You are not admin");
    }
}

