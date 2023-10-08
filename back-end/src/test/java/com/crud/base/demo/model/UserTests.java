package com.crud.base.demo.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserTests {


    @Test
    public void getAuthoritiesUserTest() {

        User user = new User(null, "email@email.com", "password", UserRole.ROLE_USER, null);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_USER.name())));
    }

    @Test
    public void getAuthoritiesManagerTest() {

        User user = new User(null, "email@email.com", "password", UserRole.ROLE_MANAGER, null);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_MANAGER.name())));
    }

    @Test
    public void getAuthoritiesAdminContainsUserTest() {

        User user = new User(null, "email@email.com", "password", UserRole.ROLE_MANAGER, null);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_USER.name())));
    }

    @Test
    public void getAuthoritiesAdminContainsManagerTest() {

        User user = new User(null, "email@email.com", "password", UserRole.ROLE_MANAGER, null);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_USER.name())));
    }

    @Test
    public void getAuthoritiesUserDoesNotContainsAdminTest() {

        User user = new User(null, "email@email.com", "password", UserRole.ROLE_USER, null);
        assertFalse(user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name())));
    }
}
