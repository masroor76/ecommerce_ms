package com.baloch.auth.service;

import com.baloch.auth.model.UserCredentials;
import com.baloch.auth.model.UserPrincipal;
import com.baloch.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserCredentials userCredentials = authRepository.findByUsername(username);

        if(userCredentials==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User Not Found");
        }

        return new UserPrincipal(userCredentials);
    }
}
