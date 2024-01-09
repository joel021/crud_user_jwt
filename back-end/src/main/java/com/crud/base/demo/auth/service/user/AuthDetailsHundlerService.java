package com.crud.base.demo.auth.service.user;

import com.crud.base.demo.auth.model.User;
import com.crud.base.demo.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthDetailsHundlerService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByEmail(username);
        if (!users.isEmpty()){
            return users.get(0);
        }else{
            return null;
        }
    }
}
