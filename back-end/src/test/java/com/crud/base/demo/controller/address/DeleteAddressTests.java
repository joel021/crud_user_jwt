package com.crud.base.demo.controller.address;


import com.crud.base.demo.TestsUtils;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.model.UserRole;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.address.AddressService;
import com.crud.base.demo.service.user.UserService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteAddressTests {

    @Inject
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    private HashMap<String, Object> userAuth;

    private Address addressToBeDeleted;


    @BeforeEach
    public void beforeAach() throws ResourceAlreadyExists, ResourceNotFoundException {
        User userCreated = userService.signup(new User(null, "userAuthdAddressDelete@gmail.com", "password", UserRole.ROLE_ADMIN, null));
        userCreated.setPassword("password");
        userAuth = userService.signin(userCreated);
        userAuth.put("id", userCreated.getId());

        addressToBeDeleted = new Address("countryDelete", "stateDelete", "cityDelete", "districtDelete", "street", 0, "44580-000");
        addressToBeDeleted = addressService.create(userCreated.getId(), addressToBeDeleted);
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteById((UUID) userAuth.get("id"));
    }

    @Test
    public void dreamDelete() throws Exception {

        mockMvc.perform(
                        delete("/users/address/"+addressToBeDeleted.getId().toString())
                                .contentType(TestsUtils.CONTENT_TYPE)
                                .header("authorization", "Bearer " + userAuth.get("token")))
                .andExpect(status().isOk())
                .andReturn();

        Address addressAssertion = null;
        try {
            addressAssertion = addressService.findById(addressToBeDeleted.getId());
        }catch (ResourceNotFoundException ignored){}

        assert ( addressAssertion == null);
    }
}
