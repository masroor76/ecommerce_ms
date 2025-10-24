package com.baloch.auth.service;

import com.baloch.auth.dto.ResponseUserDTO;
import com.baloch.auth.model.UserCredentials;
import com.baloch.auth.model.UserDetailsPrincipal;
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

        if(userCredentials ==null){
            System.out.println("User not found");
            throw new UsernameNotFoundException("User Not Found");
        }
        ResponseUserDTO responseUserDTO = toResponseUserDTO(userCredentials);
        return new UserDetailsPrincipal(responseUserDTO);
    }

    public ResponseUserDTO toResponseUserDTO(UserCredentials userCredentials){
        ResponseUserDTO resDTO = new ResponseUserDTO();
        resDTO.setUser_id(userCredentials.getUser_id());
        resDTO.setUsername(userCredentials.getUsername());
        resDTO.setPassword(userCredentials.getPassword());
        resDTO.setRole(userCredentials.getRole());
        return resDTO;
    }
}
