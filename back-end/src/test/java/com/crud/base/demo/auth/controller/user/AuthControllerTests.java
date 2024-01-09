package com.crud.base.demo.auth.controller.user;

import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.auth.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.auth.model.Role;
import com.crud.base.demo.auth.model.User;
import com.crud.base.demo.auth.repository.UserRepository;
import com.crud.base.demo.auth.service.user.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    private User dreamSigninUser;

    private User dreamSignupUser, alreadyUser, adminSettedSignupUser;

    @Before
    public void beforeAach() {

        dreamSignupUser = new User("dreamSinup@gmail.com", "password", Role.USER);
        alreadyUser = new User("signupWhenUserAlreadyExists@gmail.com", "password", Role.USER);
        adminSettedSignupUser = new User("adminSettedSignupUser@gmail.com", "password", Role.USER);

        userRepository.save(alreadyUser);

        try {
            dreamSigninUser = authService.signup(new User("dreanSigninUser@gmail.com", "password", Role.USER));
        } catch (ResourceAlreadyExists ignored){
            dreamSigninUser = userRepository.findByEmail("dreanSigninUser@gmail.com").get(0);
        }

        dreamSigninUser.setPassword("password");
    }

    @After
    public void afterEach(){
        userRepository.deleteById(dreamSigninUser.getId());
        deleteUserByEmail(dreamSignupUser.getEmail());
        deleteUserByEmail(noMatchPassSignupUser.getEmail());
        deleteUserByEmail(adminSettedSignupUser.getEmail());

        userRepository.deleteById(alreadyUser.getId());
    }

    private void deleteUserByEmail(String email){
        List<User> nonAdminSignup = userRepository.findByEmail(email);
        if(nonAdminSignup != null){
            if (!nonAdminSignup.isEmpty()) {
                userRepository.deleteById(nonAdminSignup.get(0).getId());
            }
        }
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

        mockMvc.perform(post("/users/signup").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent))
                .andExpect(status().isCreated());
    }

    @Test
    public void fetchUser() throws Exception {

        MvcResult result = mockMvc.perform(get("/users/")
                        .contentType(TestsUtils.CONTENT_TYPE)
                        .header("authorization", "Bearer " + userAuth.get("token"))
                )
                .andExpect(status().isOk());
    }

}
