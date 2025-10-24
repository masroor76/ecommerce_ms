package com.baloch.auth.filter;

import com.baloch.auth.config.SecurityConfig;
import com.baloch.auth.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Base64;

public class MyRequestResponseFilter extends OncePerRequestFilter {

    SecurityConfig securityConfig = new SecurityConfig();


    private final CustomUserDetailsService customUserDetailsService;

    public MyRequestResponseFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader==null){
            throw new ServletException("Enter Username And Password!");
        }
        String base64Credentials = authorizationHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedString = new String(decodedBytes);

        String username = decodedString.substring(0,decodedString.indexOf(":"));
        String password = decodedString.substring(decodedString.indexOf(":")+1);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username,password);

        Authentication authentication = securityConfig
                .authenticationProvider(customUserDetailsService)
                .authenticate(authToken);


        if(authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}