package com.baloch.auth.controller;

import com.baloch.auth.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public String getUser(@PathVariable String username){
        return "Login";
    }
}