package com.rajrajhans.SpringTodoApp.models;

// This class defines the structure for the response that we wil send back to the user
public class AuthResponse {
    private final String jwtToken;

    public AuthResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
