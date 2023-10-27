package org.spaghetti.chatbackend.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMessageRequest {

    @NotNull(message = "Message cannot be null!")
    @NotBlank(message = "Message cannot be blank!")
    private String message;
    @NotNull(message = "Nickname cannot be null!")
    @NotBlank(message = "Nickname cannot be blank!")
    private String nickname;

    @NotNull(message = "Chat Id cannot be null!")
    private Long chatId;

    public CreateMessageRequest() {
    }

    public CreateMessageRequest(String message, String nickname, Long chatId) {
        this.message = message;
        this.nickname = nickname;
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
