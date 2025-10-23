package com.baloch.auth.model;

import com.baloch.auth.config.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private SecurityConfig config;
    private UserCredentials UserCredentials;

    public UserPrincipal(UserCredentials user) {
        this.UserCredentials = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(UserCredentials.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return UserCredentials.getPassword();
    }

    @Override
    public String getUsername() {
        return UserCredentials.getUsername();
    }
}
