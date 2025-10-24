package com.baloch.auth.service;

import com.baloch.auth.config.SecurityConfig;
import com.baloch.auth.dto.*;
import com.baloch.auth.handlers.HandlerMethod;
import com.baloch.auth.model.UserCredentials;
import com.baloch.auth.repository.AuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final SecurityConfig customSecurityConfig;
    private HandlerMethod handlerMethod;

    public AuthService(AuthRepository authRepository,
                       SecurityConfig customSecurityConfig,
                       HandlerMethod handlerMethod) {
        this.authRepository = authRepository;
        this.customSecurityConfig = customSecurityConfig;
        this.handlerMethod = handlerMethod;
    }

    public Object register(RequestDTO userRequestBody) {
        UserCredentials userCredentials = getUserCredentialsMethod(userRequestBody);
        userCredentials.setPassword(customSecurityConfig.passwordEncoder().encode(userCredentials.getPassword()));

        try {
            ResponseDTO responseDTO = getUserCredentialsResponseMethod(authRepository.save(userCredentials));

            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(
                    201,"New User Created Successfully",
                    responseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){

            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(500,
                    e.toString());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }


    public Object updateUsername(UsernameDTO username, Authentication principal) throws Exception {
        UserDetails userDetails = (UserDetails) principal.getPrincipal();

        UserCredentials userCredentials = authRepository.findByUsername(userDetails.getUsername());
        if(userCredentials ==null){
            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(404,
                    "User Not Found Error!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }
        UserCredentials userCredentialsWithMatchingUsername = authRepository.findByUsername(username.getUsername());
        if(userCredentialsWithMatchingUsername !=null){
            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(400,
                    "Username is already taken!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(genericResponseBody);
        }
        try {
            userCredentials.setUsername(username.getUsername());
            ResponseDTO responseDTO = getUserCredentialsResponseMethod(authRepository.save(userCredentials));
            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(
                    201,"Username has been updated Successfully",
                    responseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(500,
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }


    public Object updatePassword(PasswordDTO password, Authentication principal) throws Exception {
        UserDetails userDetails = (UserDetails) principal.getPrincipal();

        UserCredentials userCredentials = authRepository.findByUsername(userDetails.getUsername());
        if(userCredentials ==null){
            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(404,
                    "User Not Found Error!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }
        try {
            userCredentials.setPassword(customSecurityConfig.passwordEncoder().encode(password.getPassword()));
            ResponseDTO responseDTO = getUserCredentialsResponseMethod(authRepository.save(userCredentials));
            GenericResponseBody genericResponseBody = handlerMethod.genericResponseBodyMethod(
                    201,"Password has been updated Successfully",
                    responseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    public UserCredentials getUserCredentialsMethod(RequestDTO userRequest){
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername(userRequest.getUsername());
        userCredentials.setPassword(userRequest.getPassword());
        return userCredentials;
    }

    public ResponseDTO getUserCredentialsResponseMethod(UserCredentials userCredentials){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setUser_id(userCredentials.getUser_id());
        responseDTO.setUsername(userCredentials.getUsername());
        responseDTO.setRole(String.valueOf(userCredentials.getRole()));
        return responseDTO;
    }
}
