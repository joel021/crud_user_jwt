package com.crud.base.demo.service.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crud.base.demo.exceptions.UserAlreadyExists;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.security.JwtHundler;

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

    public User signup(User user) throws UserAlreadyExists {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User userFound = searchUser.findByEmail(user.getEmail());
        if (userFound != null) {
            throw new UserAlreadyExists(null);
        }

        return userRepository.save(
                new User(
                        user.getId(),
                        user.getEmail(),
                        bCryptPasswordEncoder.encode(user.getPassword()),
                        Role.USER));
    }
    
}
