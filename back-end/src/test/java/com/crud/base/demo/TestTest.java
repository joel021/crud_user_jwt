package com.crud.base.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.crud.base.demo.model.Address;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTest {

    @Test
    public void parseAddressModelTest(){
        HashMap<String, Object> addressMap = new HashMap<>();
        String cep = "44530-000";
        addressMap.put("cep",cep);

        Address address = Address.parseAddress(addressMap);

        assert (cep.equals(address.getCep()));
    }

}
