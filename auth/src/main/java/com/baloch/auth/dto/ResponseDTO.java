package com.baloch.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private String user_id;
    private String username;
    private String  role;

    public ResponseDTO() {

    }
}
