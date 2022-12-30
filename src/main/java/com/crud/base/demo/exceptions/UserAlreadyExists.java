package com.crud.base.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExists extends Exception {

    private static final long serialVersionUID = 1L;
    
    public UserAlreadyExists(String message) {
        super(message);
    }
    
}