package com.crud.base.demo.auth.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExists extends ApiException {

    public ResourceAlreadyExists(String message) {
        super(message, HttpStatus.CONFLICT);
    }
    
}