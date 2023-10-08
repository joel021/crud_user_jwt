package com.crud.base.demo.controller.user;


import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUserTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private HashMap<String, Object> userAuth;

    @BeforeEach
    public void beforeAach() throws ResourceAlreadyExists {
        User userCreated = userService.signup(new User("userAuthdSearch@gmail.com", "password", Role.USER));
        userCreated.setPassword("password");
        userAuth = userService.signin(userCreated);
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteById((UUID) userAuth.get("id"));
    }

    @Test
    public void fetchUser() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/").contentType(TestsUtils.CONTENT_TYPE)
                        .header("authorization", "Bearer " + userAuth.get("token"))
                )
                .andExpect(status().isOk())
                .andReturn();

        final HashMap<String, Object> responseBody = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
        assert ( responseBody.get("id") != null);
        assert (responseBody.get("addresses") != null);
    }

}