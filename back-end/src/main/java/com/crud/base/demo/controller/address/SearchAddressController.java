package com.crud.base.demo.controller.address;

import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("users/address")
public class SearchAddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> fetchAddressById(@PathVariable String id) {
        Address addressFound;
        HashMap<String, Object> respBody = new HashMap();

        try {
            addressFound = addressService.findById(UUID.fromString(id));
        } catch (Exception err) {

            respBody.put("message", err.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(respBody);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (addressFound.getOwner().getId() == user.getId()){
            respBody.put("message", "This address does not exists."); //to the account of this user.
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(respBody);
        }
        addressFound.setOwner(null);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressFound);
    }
}
