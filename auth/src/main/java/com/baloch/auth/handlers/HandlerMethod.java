package com.baloch.auth.handlers;

import com.baloch.auth.dto.GenericResponseBody;
import org.springframework.stereotype.Component;

@Component
public class HandlerMethod {
    public GenericResponseBody genericResponseBodyMethod(int statusCode, String message, Object body){
        GenericResponseBody genericResponseBody = new GenericResponseBody();
        genericResponseBody.setHttpStatus(statusCode);
        genericResponseBody.setMessage(message);
        genericResponseBody.setBody(body);
        return genericResponseBody;
    }

    public GenericResponseBody genericResponseBodyMethod(int statusCode, String message){
        GenericResponseBody genericResponseBody = new GenericResponseBody();
        genericResponseBody.setHttpStatus(statusCode);
        genericResponseBody.setMessage(message);
        return genericResponseBody;
    }
}

