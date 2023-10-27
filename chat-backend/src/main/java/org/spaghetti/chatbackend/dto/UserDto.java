package org.spaghetti.chatbackend.dto;

import java.util.List;

public class UserDto {
    private Long id;
    private String nickname;

    private List<MessageDtoMutual> messageDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<MessageDtoMutual> getMessageDtoList() {
        return messageDtoList;
    }

    public void setMessageDtoList(List<MessageDtoMutual> messageDtoList) {
        this.messageDtoList = messageDtoList;
    }

    public UserDto() {
    }

    public UserDto(Long id, String nickname, List<MessageDtoMutual> messageDtoList) {
        this.id = id;
        this.nickname = nickname;
        this.messageDtoList = messageDtoList;
    }
}
