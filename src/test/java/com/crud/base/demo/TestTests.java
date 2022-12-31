package com.crud.base.demo;

import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestTests {

    @Test
    public void ensureUniqueAddresses(){
        HashSet<Address> addressHashSet = new HashSet<>();
        Address address1 = new Address("street","state", "country", 0);
        Address address2 = new Address("street","state", "country", 0);
        Address address3 = new Address("street","state", "country", 1);

        assert(address1.equals(address2));

        assert(!address1.equals(address3));

        addressHashSet.add(address1);
        addressHashSet.add(address2);
        addressHashSet.add(address3);

        assert(addressHashSet.size() == 2);

        assert(addressHashSet.contains(address1));
        assert(addressHashSet.contains(address2));
        assert(addressHashSet.contains(address3));

        User user = new User();
        user.setAddresses(addressHashSet);

        assert(user.getAddresses().contains(address2));
    }

}
