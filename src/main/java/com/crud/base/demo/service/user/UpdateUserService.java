package com.crud.base.demo.service.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crud.base.demo.repository.UserRepository;

@Service
public class UpdateUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchUserService searchUserService;

    @Autowired
    private AddressService addressService;

    public User updateAddresses(User user) throws ResourceNotFoundException {

        User userFound = searchUserService.findById(user.getId());

        for (Address address: userFound.getAddresses()){
            if(user.getAddresses().contains(address)){
                userFound.removeAddress(address);
            }
        }

        for(Address address: user.getAddresses()){
            address.setAddressKey(user.getId()+address.getAddressKey());
            Address addressUpdated = addressService.create(address);
            userFound.appendAddress(addressUpdated);
        }

        return userRepository.save(userFound);
    }

    public User updateEmailAndPassword(User user) throws ResourceNotFoundException  {
        User userFound = searchUserService.findById(user.getId());

        if(user.getEmail() != null && !"".equals(user.getEmail())){
            userFound.setEmail(user.getEmail());
        }

        if(user.getPassword() != null && !"".equals(user.getPassword())){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userFound.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(userFound);
    }

    public User updateRole(User user) throws ResourceNotFoundException {

        User userFound = searchUserService.findById(user.getId());
        userFound.setRole(user.getRole());
        return userRepository.save(userFound);
    }

}
