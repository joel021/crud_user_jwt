package com.crud.base.demo.controller.user;

import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.User;
import com.crud.base.demo.model.UserRole;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SigninTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User dreamSigninUser;

    @BeforeEach
    public void beforeAach() {
        try {
            dreamSigninUser = userService.signup(new User(null, "dreanSigninUser@gmail.com", "password", UserRole.ROLE_ADMIN, null));
        }catch (ResourceAlreadyExists ignored){
            dreamSigninUser = userRepository.findByEmail("dreanSigninUser@gmail.com").get(0);
        }

        dreamSigninUser.setPassword("password");
    }

    @AfterEach
    public void afterEach(){
        userRepository.deleteById(dreamSigninUser.getId());
    }

    @Test
    public void dreamSignin() throws Exception {
        HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("email", dreamSigninUser.getEmail());
        userObject.put("password", dreamSigninUser.getPassword());

        String bodyContent = TestsUtils.objectToJson(userObject);

        final MvcResult result = mockMvc.perform(post("/users/signin").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                .andExpect(status().isOk())
                .andReturn();

        final HashMap<String, Object> responseBody = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
        assert(!responseBody.get("token").equals(""));
    }

    @Test
    public void signinWhenWrongPass() throws Exception {
        HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("email", dreamSigninUser.getEmail());
        userObject.put("password", dreamSigninUser.getPassword()+"ss");

        String bodyContent = TestsUtils.objectToJson(userObject);

        mockMvc.perform(post("/users/signin").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    public void signinWithoutEmail() throws Exception {
        HashMap<String, Object> userObject = new HashMap<>();
        userObject.put("password", dreamSigninUser.getPassword()+"ss");

        String bodyContent = TestsUtils.objectToJson(userObject);

        mockMvc.perform(post("/users/signin").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }

}
