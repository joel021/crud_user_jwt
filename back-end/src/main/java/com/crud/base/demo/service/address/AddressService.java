package com.crud.base.demo.service.address;

import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;
import com.crud.base.demo.repository.AddressRepository;
import com.crud.base.demo.repository.UserRepository;
import com.crud.base.demo.service.user.SearchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SearchUserService searchUserService;

    @Autowired
    private UserRepository userRepository;

    public Address create(UUID userId, Address address) throws ResourceNotFoundException, ResourceAlreadyExists {

        User userFound = searchUserService.findById(userId);

        if(userFound.getAddresses().contains(address)){
            throw new ResourceAlreadyExists("Address already associated with this user.");
        }
        userFound.setAddresses(null);
        address.setOwner(userFound);
        return addressRepository.save(address);
    }

    public Address update(UUID userId, Address address) throws ResourceNotFoundException {
        User userFound = searchUserService.findById(userId);
        userFound.setAddresses(null);
        address.setOwner(userFound);
        return addressRepository.save(address);
    }

    public void deleteById(UUID id){
        addressRepository.deleteById(id);
    }

    public Address findById(UUID id) throws ResourceNotFoundException{
        Optional<Address> a = addressRepository.findById(id);
        if (a.isPresent()){
            return a.get();
        }
        throw new ResourceNotFoundException("Can't find address requested.");
    }

    public void deleteAddressById(UUID userId, UUID addressId) throws ResourceNotFoundException {
        User userFound = searchUserService.findById(userId);
        Address address = new Address();
        userFound.removeAddress(address);

        userRepository.save(userFound);
        addressRepository.deleteById(addressId);
    }
}
