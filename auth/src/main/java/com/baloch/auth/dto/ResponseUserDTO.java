package com.baloch.auth.dto;

import com.baloch.auth.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class ResponseUserDTO {
    private String user_id;
    private String username;
    private String password;
    private Role role;

    public ResponseUserDTO() {

    }
}
