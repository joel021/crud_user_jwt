package com.crud.base.demo.controller.address;

import com.crud.base.demo.exceptions.NotAllowedException;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("users/address")
public class DeleteAddress {

    @Autowired
    private AddressService addressService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable String id){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HashMap<String, String> resp = new HashMap<>();

        try {
            addressService.deleteById(user.getId(), UUID.fromString(id));
        } catch (ResourceNotFoundException e) {

            resp.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(resp);

        } catch (NotAllowedException e) {
            resp.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(resp);
        }

        resp.put("message", "Deleted successfully.");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resp);

    }
}
