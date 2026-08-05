package org.JamesWeb.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.JamesWeb.exception.AuthenticationPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SessionCheckFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if(request.getServletPath().equals("/payment") ){
            return false;
        }
        else return !request.getServletPath().equals("/merchant/transaction");
    }
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String auth1 = request.getHeader("Authorization");

        if(auth1 !=null && authenticationService.request(request) && auth1.startsWith("Bearer ") ){
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            "Merchant-Details",null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        else{
            throw new AuthenticationPasswordException("AuthenticationMismatch");
        }
        filterChain.doFilter(request, response);
    }
}
