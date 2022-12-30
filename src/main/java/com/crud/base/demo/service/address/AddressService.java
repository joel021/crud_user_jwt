package com.crud.base.demo.service.address;

import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.AddressRepository;
import com.crud.base.demo.service.user.UpdateUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address create(Address address) {

        Address addressFound = addressRepository.findByAddressKey(address.getAddressKey());

        if(addressFound == null){
            return addressRepository.save(address);
        }else{
            return addressFound;
        }
    }

    public void deleteById(UUID id){
        addressRepository.deleteById(id);
    }

    public void deleteByAddressKey(String key){
        addressRepository.deleteByAddressKey(key);
    }

}
