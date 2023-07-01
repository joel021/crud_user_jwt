package com.crud.base.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.crud.base.demo.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByEmail(String email);

}
