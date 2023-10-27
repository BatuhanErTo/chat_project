package org.spaghetti.chatbackend.controller;

import jakarta.validation.Valid;
import org.spaghetti.chatbackend.dto.request.CreateMessageRequest;
import org.spaghetti.chatbackend.dto.response.create.MessageCreatedResponse;
import org.spaghetti.chatbackend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/messages")
@CrossOrigin("*")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/createMessage")
    public ResponseEntity<MessageCreatedResponse> createMessage(@Valid @RequestBody CreateMessageRequest createMessageRequest) {
        return ResponseEntity.ok(messageService.saveMessage(createMessageRequest));
    }

}
