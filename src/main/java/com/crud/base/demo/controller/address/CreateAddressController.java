package com.crud.base.demo.controller.address;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.user.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/address")
public class CreateAddressController {

    @Autowired
    private CreateUserService createUserService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid Address address) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createUserService.addAddressById(user.getId(), address));
        }catch (ResourceNotFoundException err){
            //TODO: Will must never happen because the user must be authenticated to access this route.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The system can't create this address because it can't find the owner user.");
        }
    }

}
