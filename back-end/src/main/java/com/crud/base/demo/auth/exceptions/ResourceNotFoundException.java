package com.crud.base.demo.auth.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

  public ResourceNotFoundException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}