package com.crud.base.demo.controller.address;


import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.Role;
import com.crud.base.demo.model.User;
import com.crud.base.demo.model.UserExtended;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.address.AddressService;
import com.crud.base.demo.service.user.AuthService;
import com.crud.base.demo.service.user.CreateUserService;
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

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateAddressTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;
    
    @Autowired
    private AddressService addressService;

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private DeleteUserService deleteUserService;

    private UserExtended testerUser;
    private HashMap<String, Object> authUser;

    private Address addressToCreate, addressAlreadyExists;

    @Before
    public void beforeAach() throws ResourceNotFoundException, ResourceAlreadyExists {
        addressAlreadyExists = new Address("country", "state", "city", "district", "street", 0);

        try {
            User testerUser = authService.signup(new User("addressTesterUser@gmail.com", "password", Role.USER));
            this.testerUser = new UserExtended(testerUser);
        } catch (ResourceAlreadyExists ignored) {
            User testerUser = userRepository.findByEmail("addressTesterUser@gmail.com").get(0);
            this.testerUser = new UserExtended(testerUser);
        }

        testerUser.setPassword("password");

        authUser = authService.signin(testerUser);
        authUser.put("password", "password");

        addressAlreadyExists = addressService.create(testerUser.getId(), addressAlreadyExists);

        addressToCreate = new Address("country1", "state1", "city1", "district1", "street1", 1);
    }

    @After
    public void afterEach() throws ResourceNotFoundException {

        addressService.deleteAddressById(testerUser.getId(), addressAlreadyExists.getId());

        if(addressToCreate.getId() != null){
            addressService.deleteAddressById(testerUser.getId(), addressToCreate.getId());
        }
        userRepository.deleteById(testerUser.getId());

    }

    @Test
    public void dreamCreate() throws Exception {

        String bodyContent = TestsUtils.objectToJson(addressToCreate);

        MvcResult result = mockMvc.perform(post("/users/address/").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent)
                        .header("authorization", "Bearer " + authUser.get("token"))
                )
                .andExpect(status().isCreated())
                .andReturn();

        final HashMap<String, Object> responseBody = new ObjectMapper().readValue(result.getResponse().getContentAsString(), HashMap.class);
        addressToCreate.setId(UUID.fromString( (String) responseBody.get("id")));
    }

    @Test
    public void createWithoutAuth() throws Exception {

        String bodyContent = TestsUtils.objectToJson(addressToCreate);

        mockMvc.perform(post("/users/address/").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent)
                )
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void createWhenAlreadyExists() throws Exception {
        HashMap<String, Object> addressAlreadyExists = new HashMap<>();
        addressAlreadyExists.put("country", this.addressAlreadyExists.getCountry());
        addressAlreadyExists.put("state", this.addressAlreadyExists.getState());
        addressAlreadyExists.put("city", this.addressAlreadyExists.getCity());
        addressAlreadyExists.put("street", this.addressAlreadyExists.getStreet());
        addressAlreadyExists.put("number", this.addressAlreadyExists.getNumber());
        addressAlreadyExists.put("district", this.addressAlreadyExists.getDistrict());

        String bodyContent = TestsUtils.objectToJson(addressAlreadyExists);

        mockMvc.perform(post("/users/address/").contentType(TestsUtils.CONTENT_TYPE)
                        .content(bodyContent)
                        .header("authorization", "Bearer " + authUser.get("token"))
                )
                .andExpect(status().isConflict())
                .andReturn();
    }
}
