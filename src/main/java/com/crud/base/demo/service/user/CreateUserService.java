package com.crud.base.demo.service.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateUserService {

    @Autowired
    private SearchUserService searchUserService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    public Address addAddressById(UUID userId, Address address) throws ResourceNotFoundException {

        User userFound = searchUserService.findById(userId);
        Address addressCreated = addressService.create(address);
        userFound.appendAddress(addressCreated);
        userRepository.save(userFound);

        return addressCreated;
    }
}
