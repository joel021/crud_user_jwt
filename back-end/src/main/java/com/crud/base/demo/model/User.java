package com.crud.base.demo.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity(name = "user_")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable=false, unique=true, nullable=false)
    private UUID id;

    @Nonnull
    @NotBlank(message = "The email is required.")
    private String email;

    @Nonnull
    @NotBlank(message = "Password is required")
    private String password;

    private String role;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "owner")
    private Set<Address> addresses;

    public User(){
        this.addresses = new HashSet<>();
    }

    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
        this.addresses = new HashSet<>();
    }
    public User(UUID id, String email, String password, String role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.addresses = new HashSet<>();
    }
    @Override
    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        User user = (User) object;
        return Objects.equals(this.email, user.email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public UUID getId(){
        return id;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }


    public void removeAddress(Address address){
        this.addresses.remove(address);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Role.getGrandedAuthorities(this.role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}