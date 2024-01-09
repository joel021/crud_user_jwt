package com.crud.base.demo.auth.service.user;

import com.crud.base.demo.auth.repository.UserRepository;
import com.crud.base.demo.auth.service.address.AddressService;
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

}
