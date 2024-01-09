package com.crud.base.demo.auth.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private String authority;
    public static final String READ = "READ", WRITE = "WRITE",
            READ_ALL = "READ_ALL", WRITE_ALL = "WRITE_ALL";

    public Authority(){
        this.authority = READ;
    }

    public Authority(String role){

    }
    @Override
    public String getAuthority() {
        return this.authority;
    }

}