package com.baloch.auth.filter;

import com.baloch.auth.service.CustomUserDetailsService;
import com.baloch.auth.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authInput = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        String jwtToken = "";
        String username="";

        if (authInput == null ) {
            if(Objects.equals(uri, "/api/v1/auth/register") || Objects.equals(uri, "/api/v1/auth/login")) {
                filterChain.doFilter(request, response);
            }
            response.setStatus(500);
            return;
        }

        if (authInput.startsWith("Bearer ")) {
            jwtToken = authInput.substring(7);
            username = jwtService.extractUsername(jwtToken);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if(!jwtService.validateToken(jwtToken,userDetails)) {
                response.setStatus(401);
                return;
            }
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        filterChain.doFilter(request,response);
    }
}