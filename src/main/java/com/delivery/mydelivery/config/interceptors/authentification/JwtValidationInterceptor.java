package com.delivery.mydelivery.config.interceptors.authentification;

import com.delivery.mydelivery.services.jwt.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class JwtValidationInterceptor implements HandlerInterceptor {
    @Autowired
    JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false; // Do not proceed with further processing
        }
        try {
            String jwtToken = request.getHeader("Authorization");
            if(jwtToken != null &&  jwtService.isValid(jwtToken.replace("Bearer ", ""))) {
                JwtAuthentication authentication = new JwtAuthentication(jwtToken);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                // intlass Spring Security
                //SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
            else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return false;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            return false;
        }
    }

}
