package com.crud.base.demo.controller.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.crud.base.demo.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.user.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody @Valid User loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signin(loginRequest));
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
            authService.signup(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.signin(user));
        }catch(ResourceAlreadyExists err){
            HashMap<String, Object> resp = new HashMap<>();
            resp.put("errors", new ArrayList<>(Collections.singletonList("This user already exists.")));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
        }
        
    }

}
