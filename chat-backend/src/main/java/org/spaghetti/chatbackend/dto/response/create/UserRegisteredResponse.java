package org.spaghetti.chatbackend.dto.response.create;

public class UserRegisteredResponse {
    private String username;

    public UserRegisteredResponse() {
    }

    public UserRegisteredResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
