package com.crud.base.demo.service.address;

import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.AddressRepository;
import com.crud.base.demo.service.user.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SearchUserService searchUserService;

    public Address create(UUID userId, Address address) throws ResourceNotFoundException, ResourceAlreadyExists {

        User userFound = searchUserService.findById(userId);

        if(userFound.getAddresses().contains(address)){
            throw new ResourceAlreadyExists("Address already associated with this user.");
        }
        userFound.setAddresses(null);
        address.setOwner(userFound);
        return addressRepository.save(address);
    }

    public void deleteById(UUID id){
        addressRepository.deleteById(id);
    }

}
