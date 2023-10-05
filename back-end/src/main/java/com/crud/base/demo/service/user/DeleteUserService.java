package com.crud.base.demo.service.user;

import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
