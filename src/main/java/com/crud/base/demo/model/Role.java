package com.crud.base.demo.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;

public class Role implements GrantedAuthority {

    public static final String ADMIN = "ADMIN", USER = "USER";

    private String role;
    
    public Role(){
        this.role = USER;
    }
    public Role(String role){
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
