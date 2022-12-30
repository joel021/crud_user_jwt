package com.crud.base.demo.service.user;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SearchUserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        List<User> users = userRepository.findByEmail(email);

        if (users.isEmpty()){
            return null;
        }else{
            return users.get(0);
        }
    }

    public User findById(UUID id) throws ResourceNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if(!result.isPresent()){
            throw new ResourceNotFoundException("User with id = "+id+" do not exists.");
        }

        return result.get();
    }
}
