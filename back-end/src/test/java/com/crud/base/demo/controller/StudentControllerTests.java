package com.crud.base.demo.controller;

import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.service.user.UserService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class StudentControllerTests {

    @Inject
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private User adminUser;
    private String token;


    @BeforeEach
    public void setup() throws ResourceAlreadyExists {

        adminUser = new User(UUID.randomUUID(), "Admin", "password", Role.ADMIN);
        userService.signup(adminUser);
        token = userService.signin(adminUser).get("token").toString();
    }

    @AfterEach
    public void afterEach() {
        userService.deleteUserByEmail(adminUser.getEmail());
    }

    @Test
    public void findStudentByRegisterNotExistsTest() throws Exception {

        mockMvc.perform(
                        get("/student/000293")
                                .contentType(TestsUtils.CONTENT_TYPE)
                                .header("authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }
}
