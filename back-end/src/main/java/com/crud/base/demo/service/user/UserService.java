package com.crud.base.demo.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.security.JwtHundler;

@Service
public class UserService {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtHundler jwtHundler;

    @Autowired
    private UserRepository userRepository;


    public HashMap<String, Object> signin(User loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtHundler.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        HashMap<String, Object> credentials = new HashMap<>();
        credentials.put("email", userDetails.getEmail());
        credentials.put("id", userDetails.getId());
        credentials.put("token", jwt);
        credentials.put("role", userDetails.getRole());

        return credentials;
    }

    public User signup(User user) throws ResourceAlreadyExists {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User userFound = findByEmail(user.getEmail());
        if (userFound != null) {
            throw new ResourceAlreadyExists(null);
        }

        return userRepository.save(
                new User(
                        user.getId(),
                        user.getEmail(),
                        bCryptPasswordEncoder.encode(user.getPassword()),
                        Role.USER));
    }


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

    public void deleteUserByEmail(String email){
        User userFound = findByEmail(email);
        if(userFound != null){
            userRepository.deleteById(userFound.getId());
        }
    }


    public User updateEmailAndPassword(User user) throws ResourceNotFoundException  {
        User userFound = findById(user.getId());

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

        User userFound = findById(user.getId());
        userFound.setRole(user.getRole());
        return userRepository.save(userFound);
    }

}
