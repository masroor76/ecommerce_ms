package com.baloch.user.dto;

import com.baloch.user.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private String email;

    private String username;

    private String name;

    private String password;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;
}
