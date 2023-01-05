package com.crud.base.demo.controller.address;

import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.NotAllowedException;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.address.AddressService;
import com.crud.base.demo.service.user.AuthService;
import com.crud.base.demo.service.user.DeleteUserService;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchAddressTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DeleteUserService deleteUserService;

    private HashMap<String, Object> userAuth;

    private Address addressAlreadyExists;


    @Before
    public void beforeAach() throws ResourceAlreadyExists, ResourceNotFoundException {
        User userCreated = authService.signup(new User("userAuthdAddressSearch@gmail.com", "password", Role.USER));
        userCreated.setPassword("password");
        userAuth = authService.signin(userCreated);
        userAuth.put("id", userCreated.getId());

        addressAlreadyExists = new Address("country45", "state45", "city45", "district", "street", 0);
        addressAlreadyExists = addressService.create(userCreated.getId(), addressAlreadyExists);
    }

    @After
    public void afterEach() throws ResourceNotFoundException, NotAllowedException {
        addressService.deleteById((UUID) userAuth.get("id"), addressAlreadyExists.getId());
        userRepository.deleteById((UUID) userAuth.get("id"));
    }

    @Test
    public void findAddressById() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/users/address/"+addressAlreadyExists.getId().toString())
                        .contentType(TestsUtils.CONTENT_TYPE)
                        .header("authorization", "Bearer " + userAuth.get("token")))
                .andExpect(status().isOk())
                .andReturn();

        final HashMap<String, Object> responseBody = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
        assert ( responseBody.get("id") != null);
    }

}