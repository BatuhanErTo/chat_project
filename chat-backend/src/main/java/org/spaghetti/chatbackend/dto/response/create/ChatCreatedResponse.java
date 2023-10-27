package org.spaghetti.chatbackend.dto.response.create;

public class ChatCreatedResponse {
    private Long id;

    public ChatCreatedResponse(Long id) {
        this.id = id;
    }

    public ChatCreatedResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
