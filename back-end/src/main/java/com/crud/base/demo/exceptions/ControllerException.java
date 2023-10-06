package com.crud.base.demo.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ControllerException extends Exception {

    private HttpStatus status;

    public ControllerException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
