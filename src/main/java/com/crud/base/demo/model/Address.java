package com.crud.base.demo.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable=false, unique=true, nullable=false)
    private UUID id;
    @Column(name="addressKey", updatable = true, unique = true, nullable = false)
    private String addressKey;

    @NotBlank(message = "The street name is required.")
    private String street;

    @NotBlank(message = "The state name is required.")
    private String state;

    @NotBlank(message = "The country name is required.")
    private String country;

    @NotNull(message = "The number is required.")
    private int number;

    public Address(String street, String state, String country, int number){
        this.addressKey = street+"-"+state+"-"+country+"-"+number;
        this.street = street;
        this.state = state;
        this.country = country;
        this.number = number;
    }
    public Address (){}

    @Override
    public boolean equals(Object arg0) {

        if (arg0 == null){
            return false;
        }
        
        Address address = (Address) arg0;

        if(Objects.equals(address.id, this.id)){
            return true;
        }

        return Objects.equals(address.street, this.street)
                && Objects.equals(address.state, this.state)
                && Objects.equals(address.country, this.country)
                && Objects.equals(address.number, this.number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }
}
