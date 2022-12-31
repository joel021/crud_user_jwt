package com.crud.base.demo.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", updatable=false, unique=true, nullable=false)
    private UUID id;

    @NotBlank(message = "The street name is required.")
    private String street;

    @NotBlank(message = "The state name is required.")
    private String state;

    @NotBlank(message = "The country name is required.")
    private String country;

    @NotNull(message = "The number is required.")
    private int number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Address(String street, String state, String country, int number){
        this.street = street;
        this.state = state;
        this.country = country;
        this.number = number;
    }
    public Address (){}

    @Override
    public boolean equals(Object arg0) {

        if (!(arg0 instanceof Address)){
            return false;
        }

        Address address = (Address) arg0;
        return Objects.equals(this.number+"-"+this.street+"-"+this.state+"-"+this.country,
                address.getNumber()+"-"+address.getStreet()+"-"+address.getState()+"-"+address.getCountry());
    }

    @Override
    public int hashCode(){
        return 1;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
