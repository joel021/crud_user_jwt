package com.crud.base.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExists extends ControllerException {

    public ResourceAlreadyExists(String message) {
        super(HttpStatus.CONFLICT, message);
    }
    
}