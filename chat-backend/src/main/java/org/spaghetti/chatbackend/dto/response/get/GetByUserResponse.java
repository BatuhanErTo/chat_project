package org.spaghetti.chatbackend.dto.response.get;

import java.util.List;

public class GetByUserResponse {
    private String nickname;
    private List<String> messages;

    public GetByUserResponse() {
    }

    public GetByUserResponse(String nickname, List<String> messages) {
        this.nickname = nickname;
        this.messages = messages;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }


}
