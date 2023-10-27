package org.spaghetti.chatbackend.dto;

import java.time.LocalDateTime;

public class MessageDtoMutual {
    Long id;
    private String message;
    private LocalDateTime timeToReceived;

    public MessageDtoMutual() {
    }

    public MessageDtoMutual(Long id, String message, LocalDateTime timeToReceived) {
        this.id = id;
        this.message = message;
        this.timeToReceived = timeToReceived;
    }

    public LocalDateTime getTimeToReceived() {
        return timeToReceived;
    }

    public void setTimeToReceived(LocalDateTime timeToReceived) {
        this.timeToReceived = timeToReceived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
