package com.baloch.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class User {

    private String user_id;

    private String email;

    private String username;

    private String name;

    private String password;

    private int age;

    private Role role;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public User() {

    }
}
