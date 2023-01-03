package com.crud.base.demo.controller.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.crud.base.demo.TestsUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;

import jakarta.inject.Inject;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignupTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User dreamSignupUser, alreadyUser, noMatchPassSignupUser, adminSettedSignupUser;

    private void deleteUserByEmail(String email){
        List<User> nonAdminSignup = userRepository.findByEmail(email);
        if(nonAdminSignup != null){
            if (!nonAdminSignup.isEmpty()) {
                userRepository.deleteById(nonAdminSignup.get(0).getId());
            }
        }
    }

    @Before
    public void beforeAach(){
        dreamSignupUser = new User("dreamSinup@gmail.com", "password", Role.USER);
        alreadyUser = new User("signupWhenUserAlreadyExists@gmail.com", "password", Role.USER);
        noMatchPassSignupUser = new User("noMatchPassUser@gmail.com", "password", Role.USER);
        adminSettedSignupUser = new User("adminSettedSignupUser@gmail.com", "password", Role.USER);

        userRepository.save(alreadyUser);
    }

    @After
    public void afterEach(){
        deleteUserByEmail(dreamSignupUser.getEmail());
        deleteUserByEmail(noMatchPassSignupUser.getEmail());
        deleteUserByEmail(adminSettedSignupUser.getEmail());

        userRepository.deleteById(alreadyUser.getId());
    }

    @Test
    public void dreamSinup() throws Exception {

        HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("email", dreamSignupUser.getEmail());
        userObject.put("password", dreamSignupUser.getPassword());
        userObject.put("passwordConfirmation", dreamSignupUser.getPassword());

        String bodyContent = TestsUtils.objectToJson(userObject);

        mockMvc.perform(post("/users/signup").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                .andExpect(status().isCreated());

        deleteUserByEmail("dreamSinup@gmail.com");
    }

    @Test
    public void signupWhenUserAlreadyExists() throws Exception {

        HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("email", alreadyUser.getEmail());
        userObject.put("password", alreadyUser.getPassword());
        userObject.put("passwordConfirmation", alreadyUser.getPassword());

        String bodyContent = TestsUtils.objectToJson(userObject);

        mockMvc.perform(post("/users/signup").contentType(TestsUtils.CONTENT_TYPE)
                .content(bodyContent))
                .andExpect(status().isConflict());
    }

    @Test
    public void whenUserPasswordNotMatch() throws Exception {

        HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("email", noMatchPassSignupUser.getEmail());
        userObject.put("password", noMatchPassSignupUser.getPassword());
        userObject.put("password_confirmation", noMatchPassSignupUser.getPassword()+"ss");

        String bodyContent = TestsUtils.objectToJson(userObject);

        mockMvc.perform(post("/users/signup").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void whenUserAdminSetted() throws Exception {
        final HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("email", adminSettedSignupUser.getEmail());
        userObject.put("password", adminSettedSignupUser.getPassword());
        userObject.put("passwordConfirmation", adminSettedSignupUser.getPassword());
        userObject.put("role", adminSettedSignupUser.getRole());

        final String bodyContent = TestsUtils.objectToJson(userObject);

        final MvcResult result = mockMvc.perform(post("/users/signup").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                        .andExpect(status().isCreated())
                        .andReturn();

        final HashMap<String, Object> responseBody = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
        assert(responseBody.get("role") != null);
        assert(Role.USER.equals(responseBody.get("role")));
        assert(responseBody.get("token") != null);
    }

}