package com.crud.base.demo.controller.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.user.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("users")
public class Search {

    @Autowired
    private SearchUserService searchUserService;

    @GetMapping("/")
    public ResponseEntity<?> fetchUser(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(searchUserService.findById(user.getId()));
        }catch (ResourceNotFoundException e){
            HashMap<String, Object> errorBody = new HashMap<>();
            errorBody.put("message", "The user have not a valid authentication. Please, logout and try to signin again.");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(errorBody);
        }
    }
}
