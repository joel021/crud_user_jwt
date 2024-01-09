package com.crud.base.demo.auth.repository;

import java.util.List;
import java.util.UUID;

import com.crud.base.demo.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByEmail(String email);

}
