package com.crud.base.demo.service.address;

import com.crud.base.demo.model.Address;
import com.crud.base.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public void deleteById(UUID id){
        addressRepository.deleteById(id);
    }

}
