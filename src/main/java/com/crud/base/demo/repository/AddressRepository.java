package com.crud.base.demo.repository;

import com.crud.base.demo.model.Address;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    @SQLDelete(sql="SET FOREIGN_KEY_CHECKS=OFF;DELETE from address a where a.addressKey = ?1;SET FOREIGN_KEY_CHECKS=TRUE;")
    void deleteByAddressKey(String addressKey);

    Address findByAddressKey(String addressKey);
}
