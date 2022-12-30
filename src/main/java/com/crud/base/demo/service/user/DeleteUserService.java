package com.crud.base.demo.service.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.address.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserService {

    @Autowired
    private SearchUserService searchUserService;

    @Autowired
    private UpdateUserService updateUserService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    public void deleteUserByEmail(String email){
        User userFound = searchUserService.findByEmail(email);
        if(userFound != null){
            userRepository.deleteById(userFound.getId());
        }
    }

    public void deleteAddressByAddressKey(UUID userId, String addressKey) throws ResourceNotFoundException {
        User userFound = searchUserService.findById(userId);
        Address address = new Address();
        address.setAddressKey(addressKey);
        userFound.removeAddress(address);

        userRepository.save(userFound);
        addressService.deleteByAddressKey(addressKey);
    }
}
