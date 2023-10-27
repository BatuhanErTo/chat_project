package org.spaghetti.chatbackend.controller;

import org.spaghetti.chatbackend.dto.response.create.ChatCreatedResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatHistoryByChatIdResponse;
import org.spaghetti.chatbackend.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatServiceImpl) {
        this.chatService = chatServiceImpl;
    }

    @PostMapping("/createChat")
    public ResponseEntity<ChatCreatedResponse> createChat(){
        return ResponseEntity.ok(chatService.createChat());
    }

    @GetMapping("/getAllChats")
    public ResponseEntity<List<GetChatResponse>> getAllChat(){
        return ResponseEntity.ok(chatService.getAllChat());
    }

    @GetMapping("/getChatHistory/{chatId}")
    public ResponseEntity<List<GetChatHistoryByChatIdResponse>> getChatById(@PathVariable Long chatId){
        return ResponseEntity.ok(chatService.getChatHistoryByChatId(chatId));
    }
}
