package com.baloch.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDTO {
    private String username;

    private String password;

    public RequestDTO() {

    }
}
