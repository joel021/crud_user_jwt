package com.crud.base.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MainController {

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.status(HttpStatus.OK).body("User Address CRUD back-end is running.");
    }

}
