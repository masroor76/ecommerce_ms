package com.baloch.user.service;

import com.baloch.user.core.handlers.HandlerMethod;
import com.baloch.user.dto.*;
import com.baloch.user.model.User;
import com.baloch.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService{
    private UserRepository userRepository;
    private HandlerMethod handler;

    public Optional<User> getUserByUsername(String username) throws Exception {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                System.out.println(user);
                return user;
            } else {
                throw new Exception("User not found");
            }
        }catch (Exception e){
            throw new Exception(e.toString());
        }
    }

    // SINGLE USER SERVICE
    public Object user(String userId){
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                UserResponse userResponse = userResponseMethod(user);

                GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(200,
                        "User with " + userId + " found successfully", userResponse);
                return ResponseEntity.status(200).body(genericResponseBody);
            }
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(404,
                    "User with " + userId + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        } catch(Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }

    // CREATE-USER SERVICE
    public Object createUser(UserRequest userRequest){
        User user = userRequestMethod(userRequest);

//        user.setPassword(securityConfig.bCryptPasswordEncoder().encode(user.getPassword()));
        try {
            User savedUser = userRepository.save(user);
            UserResponse userResponse = userResponseMethod(savedUser);
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(201,
                    "New User has been created successfully", userResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponseBody);
        } catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                    e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }

    // UPDATE SERVICE
    public Object updateUser(String userId , UserRequestUpdate userRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            GenericResponseBody genericResponseBody= handler.genericResponseBodyMethod(404,"Failed to update User with id "+userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }

        if(userRequest.getEmail() != null){
            user.setEmail(userRequest.getEmail());
        }
        if(userRequest.getUsername() != null){
            user.setUsername(userRequest.getUsername());
        }
        if(userRequest.getName() != null){
            user.setName(userRequest.getName());
        }
        if(userRequest.getAge() != user.getAge()){
            user.setAge(userRequest.getAge());
        }
        if(userRequest.getRole() != null){
            user.setRole(userRequest.getRole());
        }

        try {
            User updatedUser = userRepository.save(user);
            UserResponse userResponse = userResponseMethod(updatedUser);
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(202,"User with new Data has been updated successfully",
                    userResponse);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }

    // UPDATE PASSWORD SERVICE
    public Object updateUserPassword(String userId , UserRequestPasswordUpdate userRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            GenericResponseBody genericResponseBody= handler.genericResponseBodyMethod(404,"Failed to update User with id "+userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
        }

        user.setPassword(userRequest.getPassword());

        try {
            User updatedUser = userRepository.save(user);
            UserResponse userResponse = userResponseMethod(updatedUser);
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(202,"User with new Data has been updated successfully",
                    userResponse);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericResponseBody);
        }catch (Exception e){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
    }


    //DELETE SERVICE
    public Object deleteUser(String userId, String password){
        if(password == null){
            GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                    "Enter password to delete the user account!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
        }
        User user = userRepository.findById(userId).orElse(null);
        if(user!=null) {
            if(user.getPassword()!=password){
                GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                        "Password for the user with id "+userId+" is incorrect!");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
            }
            try {
                userRepository.deleteById(userId);
                GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(202,
                        "User has been deleted successfully");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericResponseBody);
            } catch (Exception e) {
                GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(500,
                        e.toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponseBody);
            }
        }
        GenericResponseBody genericResponseBody = handler.genericResponseBodyMethod(404,
                "User with "+userId+" not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericResponseBody);
    }

    User userRequestMethod(UserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setAge(userRequest.getAge());
        user.setRole(userRequest.getRole());
        return user;
    }

    UserResponse userResponseMethod(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUser_id());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setName(user.getName());
        userResponse.setAge(user.getAge());
        userResponse.setRole(user.getRole());
        return userResponse;
    }
}