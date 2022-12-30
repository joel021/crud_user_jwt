package com.crud.base.demo.service.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    @Autowired
    private SearchUserService searchUserService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    public User addAddresses(User user) throws ResourceNotFoundException {

        User userFound = searchUserService.findById(user.getId());

        for(Address address: user.getAddresses()){
            address.setAddressKey(user.getId()+address.getAddressKey());
            Address addressUpdated = addressService.create(address);
            userFound.appendAddress(addressUpdated);
        }

        return userRepository.save(userFound);
    }
}
