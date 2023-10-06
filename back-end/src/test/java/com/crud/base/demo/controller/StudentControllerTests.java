package com.crud.base.demo.controller;

import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.NotAllowedException;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.user.AuthService;
import jakarta.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    private HashMap<String, Object> userAuth;


    @Before
    public void setup() throws ResourceAlreadyExists {
        User userCreated = authService.signup(new User("userAuthdAddressSearch@gmail.com", "password", Role.USER));
        userCreated.setPassword("password");
        userAuth = authService.signin(userCreated);
        userAuth.put("id", userCreated.getId());
    }

    @After
    public void afterEach() throws ResourceNotFoundException, NotAllowedException {
        userRepository.deleteById((UUID) userAuth.get("id"));
    }

    @Test
    public void findStudentByRegisterNotExistsTest() throws Exception {

        mockMvc.perform(
                        get("/student/000293")
                                .contentType(TestsUtils.CONTENT_TYPE)
                                .header("authorization", "Bearer " + userAuth.get("token")))
                .andExpect(status().isNotFound());
    }
}
