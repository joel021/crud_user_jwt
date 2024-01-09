package com.crud.base.demo.auth.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collection;

public class Role {
    public static final String ADMIN = "ADMIN", USER = "USER";
    private static final HashMap<String, Set> grandedAuthorities;

    private String role;
    
    public Role(){
        this.role = USER;
    }
    public Role(String role){
        this.role = role;
    }

    static {
        grandedAuthorities = new HashMap<>();
        grandedAuthorities.put(Role.USER, new HashSet<GrantedAuthority>(
                Arrays.asList(
                        new Authority(Authority.READ),
                        new Authority(Authority.WRITE)
                )));

        grandedAuthorities.put(Role.ADMIN, new HashSet<GrantedAuthority>(Arrays.asList(
                        new Authority(Authority.READ_ALL),
                        new Authority(Authority.WRITE_ALL)
        )));
    }

    public static Collection<? extends GrantedAuthority> getGrandedAuthorities(String role){
        return grandedAuthorities.get(role);
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
