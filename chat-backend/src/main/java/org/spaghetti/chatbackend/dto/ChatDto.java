package org.spaghetti.chatbackend.dto;

import java.util.List;

public class ChatDto {
    private Long id;
    private List<MessageDtoMutual> messageDtoMutualList;

    public ChatDto(Long id, List<MessageDtoMutual> messageDtoMutualList) {
        this.id = id;
        this.messageDtoMutualList = messageDtoMutualList;
    }

    public ChatDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MessageDtoMutual> getMessageDtoList() {
        return messageDtoMutualList;
    }

    public void setMessageDtoList(List<MessageDtoMutual> messageDtoMutualList) {
        this.messageDtoMutualList = messageDtoMutualList;
    }
}
