package com.crud.base.demo.controller.address;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("users/address")
public class UpdateAddressController {

    @Autowired
    private AddressService addressService;

    @PutMapping("/")
    public ResponseEntity<?> putAddress(@RequestBody Map<String, Object> address){
        Address putAddress = Address.parseAddress(address);
        Address addressFound;
        HashMap<String, Object> respBody = new HashMap();

        try {
            addressFound = addressService.findById(putAddress.getId());
        } catch (Exception err) {

            respBody.put("message", err.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(respBody);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!addressFound.getOwner().getId().toString().equals(user.getId().toString())){
            respBody.put("message", "This address does not exists."); //to the account of this user.
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(respBody);
        }

        try{
            Address addressUpdated = addressService.update(user.getId(), putAddress);
            addressUpdated.setOwner(null);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(addressUpdated);
        }catch (ResourceNotFoundException ignored){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("The system can't update this address because it can't find the owner user.");
        }
    }

}
