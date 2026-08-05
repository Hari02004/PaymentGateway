package org.JamesWeb.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.JamesWeb.DTO.LoginRequestDto;
import org.JamesWeb.exception.LoginCredentialException;
import org.JamesWeb.exception.LoginEmailException;
import org.JamesWeb.exception.UserEmailNotFoundException;
import org.JamesWeb.model.Role;
import org.JamesWeb.model.Roles;
import org.JamesWeb.model.User_roles;
import org.JamesWeb.model.users;
import org.JamesWeb.repository.RolesRepo;
import org.JamesWeb.repository.UserRepository;
import org.JamesWeb.repository.UserRolesRepo;
import org.JamesWeb.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private UserRolesRepo userRolesRepo;
    public void register(users users){
        if(userRepository.existsByEmail(users.getEmail())){
            throw new LoginEmailException("Email already registered");
        }
        users user = new users();
        user.setEmail(users.getEmail());
        user.setName(users.getName());
        user.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(user);
        Roles roles = rolesRepo.findByRole(Role.ROLE_USER).orElseThrow();
        User_roles userRoles = new User_roles();
        userRoles.setUsers(user);
        userRoles.setRole(roles);
        userRoles.setCreatedAt(LocalDateTime.now());
        userRoles.setAssignedBy(Role.ROLE_ADMIN);
        userRolesRepo.save(userRoles);
    }
    public void login(LoginRequestDto requestDto, HttpServletRequest request){
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(requestDto.getUsername(),requestDto.getPassword());
            Authentication authenticated =  authenticationManager.authenticate(authentication);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticated);

            SecurityContextHolder.setContext(context);

            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = request.getSession(true);

            newSession.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );
           users users = userRepository.findByEmail(requestDto.getUsername()).
                   orElseThrow(
                           ()->new UserEmailNotFoundException("Email not found"));
           java.util.List<User_roles> userRoles = userRolesRepo.findByUsersId(users.getId());
            boolean isAdmin = userRoles.stream()
                    .anyMatch(userRole ->
                            userRole.getRole().getRole() == Role.ROLE_ADMIN);
            if(isAdmin){
                String token = jwtService.generateToken(requestDto.getUsername());
            }
        } catch (Exception e) {
            throw new LoginCredentialException("Invalid email or Password");
        }
    }
}
