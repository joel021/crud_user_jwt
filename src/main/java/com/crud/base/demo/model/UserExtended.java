package com.crud.base.demo.model;

public class UserExtended extends User {
    private String password_confirmation;
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

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}