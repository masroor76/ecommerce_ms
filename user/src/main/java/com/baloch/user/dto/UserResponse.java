package com.baloch.user.dto;

import com.baloch.user.model.Role;
import lombok.Data;

@Data
public class UserResponse {
    private String id;

    private String email;

    private String username;

    private String name;

    private int age;

    private Role role;
}
