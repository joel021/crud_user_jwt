package com.crud.base.demo.controller.address;

import com.crud.base.demo.exceptions.NotAllowedException;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.address.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/")
    public ResponseEntity<List<Address>> findAll() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(addressService.findByOwner(user));
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid Address address) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(addressService.create(user.getId(), address));
        } catch (ResourceAlreadyExists | ResourceNotFoundException err){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(err.getMessage());
        }
    }

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

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> putAddress(@RequestBody Map<String, Object> address, @PathVariable String id){
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
