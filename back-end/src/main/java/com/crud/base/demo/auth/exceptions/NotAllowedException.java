package com.crud.base.demo.auth.exceptions;

import org.springframework.http.HttpStatus;

public class NotAllowedException extends ApiException {

    public NotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
