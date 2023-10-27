package org.spaghetti.chatbackend.dto.response.get;

public class GetChatResponse {
    private Long id;

    public GetChatResponse(Long id) {
        this.id = id;
    }

    public GetChatResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
