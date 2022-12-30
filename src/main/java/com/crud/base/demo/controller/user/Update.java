package com.crud.base.demo.controller.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.user.CreateUserService;
import com.crud.base.demo.service.user.UpdateUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("users")
public class Update {

    @Autowired
    private CreateUserService createUserService;

    @PostMapping("/address")
    public ResponseEntity<User> create(@RequestBody @Valid Address address) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setAddresses((Set<Address>) new HashSet<>(Arrays.asList(address)));
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createUserService.addAddresses(user));
        }catch (ResourceNotFoundException err){
            //TODO: Will must never happen because the user must be authenticated to access this route.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
        }
    }

}
