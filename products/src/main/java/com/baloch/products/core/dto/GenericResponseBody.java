package com.baloch.products.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GenericResponseBody {
    private int httpStatus;
    private String message;
    private Object body="No Content";
}
