package com.rajrajhans.SpringTodoApp.models;

// This class defines the structure for what the user is going to send us
public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest() {
        // default constructor for serialization
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
