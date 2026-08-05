package org.JamesWeb.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RedisRateLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @Nonnull HttpServletResponse response,
                             @Nonnull Object handler) throws Exception {

        String apiKey = request.getHeader("Public-Key");

        if(apiKey == null){

            response.setStatus(401);
            response.getWriter().write("API Key Missing");

            return false;
        }

        if(!rateLimiterService.allowRequest(apiKey)){

            response.setStatus(429);
            response.getWriter().write("Too Many Requests");

            return false;
        }

        return true;
    }
}
