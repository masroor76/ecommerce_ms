package com.baloch.user.dto;

import com.baloch.user.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestUpdate {
    private String email;

    private String username;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;
}
