package com.crud.base.demo.model;

public class UserExtended extends User {
    private String passwordConfirmation;
    private String token;

    public UserExtended(User user){
        super(user.getId(), user.getEmail(), user.getPassword(), user.getRole());
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserExtended(){
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}