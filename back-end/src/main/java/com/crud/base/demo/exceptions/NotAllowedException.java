package com.crud.base.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAllowedException extends Exception {
    private static final long serialVersionUID = 1L;
    public NotAllowedException(String message) {
        super(message);
    }
}
