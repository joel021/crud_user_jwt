package com.crud.base.demo.controller;

import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.Course;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.model.UserDTO;
import com.crud.base.demo.repository.CourseRepository;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.user.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTests {

    @Inject
    private MockMvc mockMvc;

    private Map<String, Object> courseToRegister;
    private Map<String, Object> userAuthenticated;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    private UserDTO testerUser;

    @Autowired
    private CourseRepository courseRepository;

    @Before
    public void setup() throws ResourceAlreadyExists {

        try {
            User testerUser = authService.signup(new User("addressTesterUser@gmail.com", "password", Role.USER));
            this.testerUser = new UserDTO(testerUser);
        } catch (ResourceAlreadyExists ignored) {
            User testerUser = userRepository.findByEmail("addressTesterUser@gmail.com").get(0);
            this.testerUser = new UserDTO(testerUser);
        }

        testerUser.setPassword("password");

        userAuthenticated = authService.signin(testerUser);
        userAuthenticated.put("password", "password");

        courseToRegister = new HashMap<>();
        courseToRegister.put("name", "Course 1");
        courseToRegister.put("year", 2022);

    }

    @After
    public void tearDown(){
        courseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void registerTest() throws Exception {

        String resultJson = mockMvc.perform(post("/users/course/").contentType(TestsUtils.CONTENT_TYPE)
                        .content(TestsUtils.objectToJson(courseToRegister))
                        .header("authorization", "Bearer " + userAuthenticated.get("token"))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final HashMap<String, Object> responseBody = new ObjectMapper().readValue(resultJson, HashMap.class);
        assert responseBody.get("id") != null;
    }

    @Test
    public void getAndSearchTest() throws Exception {

        for(int i = 0; i < 4; i++){
            courseRepository.save(new Course(UUID.fromString(userAuthenticated.get("id").toString()), "Course - "+i, 2020+i));
        }

        String resultJson = mockMvc.perform(get("/users/course/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + userAuthenticated.get("token"))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<Map<String,Object>> listOfCourses = new ObjectMapper().readValue(resultJson, List.class);
        assert listOfCourses != null;
        assert !listOfCourses.isEmpty();
        assert listOfCourses.size() >= 4;
        assert listOfCourses.get(0).get("userId") != null;

        resultJson = mockMvc.perform(get("/users/course/search")
                        .param("name", "Course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("authorization", "Bearer " + userAuthenticated.get("token"))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final List<Map<String,Object>> searchCoursesResult = new ObjectMapper().readValue(resultJson, List.class);
        assert searchCoursesResult != null;
        assert !searchCoursesResult.isEmpty();
        assert searchCoursesResult.get(0).get("userId") != null;
    }
}
