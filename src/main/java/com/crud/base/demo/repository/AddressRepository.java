package com.crud.base.demo.repository;

import com.crud.base.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
