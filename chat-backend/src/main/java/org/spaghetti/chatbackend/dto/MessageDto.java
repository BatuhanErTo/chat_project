package org.spaghetti.chatbackend.dto;


import java.time.LocalDateTime;

public class MessageDto {
    private Long id;
    private String message;
    private LocalDateTime timeToReceived;
    private UserDto userDto;

    private ChatDto chatDto;

    public MessageDto() {
    }

    public MessageDto(Long id, String message, LocalDateTime timeToReceived, UserDto userDto, ChatDto chatDto) {
        this.id = id;
        this.message = message;
        this.timeToReceived = timeToReceived;
        this.userDto = userDto;
        this.chatDto = chatDto;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ChatDto getChatDto() {
        return chatDto;
    }

    public void setChatDto(ChatDto chatDto) {
        this.chatDto = chatDto;
    }
}
