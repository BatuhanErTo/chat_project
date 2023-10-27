package org.spaghetti.chatbackend.dto.response.create;

import org.spaghetti.chatbackend.model.User;

public class MessageCreatedResponse {
    private String nickname;
    private String message;
    private Long chatId;

    public MessageCreatedResponse(String nickname, String message, Long chatId) {
        this.nickname = nickname;
        this.message = message;
        this.chatId = chatId;
    }

    public MessageCreatedResponse() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
