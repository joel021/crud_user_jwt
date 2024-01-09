package com.crud.base.demo.auth.service.user;

import java.util.HashMap;

import com.crud.base.demo.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crud.base.demo.auth.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.auth.model.Role;
import com.crud.base.demo.auth.repository.UserRepository;
import com.crud.base.demo.auth.security.JwtHundler;

@Service
public class AuthService {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtHundler jwtHundler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchUserService searchUser;

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

        User userFound = searchUser.findByEmail(user.getEmail());
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
    
}
