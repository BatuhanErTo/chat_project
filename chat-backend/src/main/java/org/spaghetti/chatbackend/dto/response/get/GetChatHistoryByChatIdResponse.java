package org.spaghetti.chatbackend.dto.response.get;

import java.time.LocalDateTime;

public class GetChatHistoryByChatIdResponse {
    private String message;
    private String nickname;
    private LocalDateTime timeToReceived;

    public GetChatHistoryByChatIdResponse() {
    }

    public GetChatHistoryByChatIdResponse(String message, String nickname, LocalDateTime timeToReceived) {
        this.message = message;
        this.nickname = nickname;
        this.timeToReceived = timeToReceived;
    }

    public LocalDateTime getTimeToReceived() {
        return timeToReceived;
    }

    public void setTimeToReceived(LocalDateTime timeToReceived) {
        this.timeToReceived = timeToReceived;
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
}
