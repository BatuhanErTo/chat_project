package org.spaghetti.chatbackend.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime timeToReceived;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;

    public Message(Long id,String message, LocalDateTime timeToReceived, User user, Chat chat) {
        this.id = id;
        this.message = message;
        this.timeToReceived = timeToReceived;
        this.user = user;
        this.chat = chat;
    }

    public Message() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public LocalDateTime getTimeToReceived() {
        return timeToReceived;
    }

    public void setTimeToReceived(LocalDateTime timeToReceived) {
        this.timeToReceived = timeToReceived;
    }
}
