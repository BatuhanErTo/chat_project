package org.spaghetti.chatbackend.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Message> messageList;

    public Chat(Long id, List<Message> messageList) {
        this.id = id;
        this.messageList = messageList;
    }

    public Chat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
