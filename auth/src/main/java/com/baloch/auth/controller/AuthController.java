package com.baloch.auth.controller;

import com.baloch.auth.dto.PasswordDTO;
import com.baloch.auth.dto.RequestDTO;
import com.baloch.auth.dto.UsernameDTO;
import com.baloch.auth.model.UserCredentials;
import com.baloch.auth.model.UserDetailsPrincipal;
import com.baloch.auth.service.AuthService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/validate-token")
    public Boolean validate(Authentication auth){
        System.out.println("Validate-Token Api");
        return  authService.validate(auth);
    }

    @PostMapping("/login")
    public Object login(@RequestBody UserCredentials user, HttpServletResponse res){
        return authService.login(user, res);
    }

    @PostMapping("/register")
    public Object register(@RequestBody RequestDTO userRequestBody){
        return authService.register(userRequestBody);
    }

    @PutMapping("/update-username")
    public Object updateUsername(@RequestBody UsernameDTO username,
                                          Authentication principal) throws Exception {
        return authService.updateUsername(username,principal);
    }

    @PutMapping("/update-password")
    public Object updatePassword(@RequestBody PasswordDTO password,
                                          Authentication principal) throws Exception {
        return authService.updatePassword(password,principal);
    }
}