package com.crud.base.demo.auth.repository;

import com.crud.base.demo.auth.model.Address;
import com.crud.base.demo.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByOwner(User user);
}
