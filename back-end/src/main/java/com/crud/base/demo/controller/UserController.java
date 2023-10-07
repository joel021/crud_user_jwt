package com.crud.base.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody @Valid User loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signin(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody @Valid UserDTO user)  {
        
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            HashMap<String, Object> resp = new HashMap<>();
            resp.put("errors",
                    new ArrayList<>(Collections.singletonList("password and password confirmation must be equal.")));
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(resp);
        }

        try {
            userService.signup(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.signin(user));
        }catch(ResourceAlreadyExists err){
            HashMap<String, Object> resp = new HashMap<>();
            resp.put("errors", new ArrayList<>(Collections.singletonList("This user already exists.")));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
        }
        
    }


    @GetMapping("/")
    public ResponseEntity<?> fetchUser(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.findById(user.getId()));
        }catch (ResourceNotFoundException e){
            HashMap<String, Object> errorBody = new HashMap<>();
            errorBody.put("message", "The user have not a valid authentication. Please, logout and try to signin again.");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(errorBody);
        }
    }

}
