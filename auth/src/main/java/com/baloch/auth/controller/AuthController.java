package com.baloch.auth.controller;

import com.baloch.auth.dto.PasswordDTO;
import com.baloch.auth.dto.RequestDTO;
import com.baloch.auth.dto.UsernameDTO;
import com.baloch.auth.service.AuthService;
import jakarta.servlet.ServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;


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