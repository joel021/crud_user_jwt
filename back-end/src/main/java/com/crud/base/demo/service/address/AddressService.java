package com.crud.base.demo.service.address;

import com.crud.base.demo.exceptions.NotAllowedException;
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
public class AddressService extends AddressServiceHandler {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SearchUserService searchUserService;


    public Address create(UUID userId, Address address) throws ResourceNotFoundException, ResourceAlreadyExists {

        User userFound = searchUserService.findById(userId);
        return addressRepository.save(super.create(address, userFound));
    }

    public Address update(UUID userId, Address address) throws ResourceNotFoundException {

        User userFound = searchUserService.findById(userId);
        return addressRepository.save(super.update(address, userFound));
    }

    public Address findById(UUID id) throws ResourceNotFoundException {

        return super.findById(addressRepository.findById(id));
    }

    public void deleteById(UUID userId, UUID addressId) throws ResourceNotFoundException, NotAllowedException {

        Address addressFound = findById(addressId);
        addressRepository.deleteById(super.deleteById(addressFound, userId).getId());
    }
}
