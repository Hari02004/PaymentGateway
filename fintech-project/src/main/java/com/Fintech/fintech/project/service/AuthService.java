package com.Fintech.fintech.project.service;

import com.Fintech.fintech.project.dto.AuthRequest;
import com.Fintech.fintech.project.dto.AuthResponse;
import com.Fintech.fintech.project.entity.User;
import com.Fintech.fintech.project.repository.UserRepository;
import com.Fintech.fintech.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String register(AuthRequest request){
        if (userRepository.existsByUserName(request.getName())) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUserName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "User Registered successfully";
    }
    public AuthResponse login(AuthRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getName(),
                request.getPassword()
        ));
        String token = jwtUtil.generateToken(request.getName());
        return mapToResponse(token);
    }

    private AuthResponse mapToResponse(String token){
        return new AuthResponse(token);
    }
}
