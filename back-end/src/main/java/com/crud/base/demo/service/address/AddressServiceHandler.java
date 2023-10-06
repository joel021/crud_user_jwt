package com.crud.base.demo.service.address;

import com.crud.base.demo.exceptions.NotAllowedException;
import com.crud.base.demo.exceptions.ResourceAlreadyExists;
import com.crud.base.demo.exceptions.ResourceNotFoundException;
import com.crud.base.demo.model.Address;
import com.crud.base.demo.model.User;

import java.util.Optional;
import java.util.UUID;

public class AddressServiceHandler {

    public Address create(Address address, User owner) throws ResourceAlreadyExists {

        if(owner.getAddresses().contains(address)) {
            throw new ResourceAlreadyExists("Address already associated with this user.");
        }
        owner.setAddresses(null);
        address.setOwner(owner);
        return address;
    }

    public Address update(Address address, User owner) {

        owner.setAddresses(null);
        address.setOwner(owner);
        return address;
    }

    public Address findById(Optional<Address> addressFound) throws ResourceNotFoundException {

        if (addressFound.isPresent()){
            return addressFound.get();
        }
        throw new ResourceNotFoundException("Can't find address requested.");
    }

    public Address deleteById(Address addressFound, UUID ownerId) throws NotAllowedException {

        if (!ownerId.equals(addressFound.getOwner().getId())) {
            throw new NotAllowedException("This address do not exist on your account.");
        }
        return addressFound;
    }
}
