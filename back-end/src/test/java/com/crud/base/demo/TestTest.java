package com.crud.base.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.crud.base.demo.model.Address;

import java.util.HashMap;

@RunWith(SpringRunner.class)
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
