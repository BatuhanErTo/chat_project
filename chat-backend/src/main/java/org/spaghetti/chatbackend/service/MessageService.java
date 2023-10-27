package org.spaghetti.chatbackend.service;



import org.spaghetti.chatbackend.dto.converter.MessageDtoConverter;
import org.spaghetti.chatbackend.dto.request.CreateMessageRequest;
import org.spaghetti.chatbackend.dto.response.create.MessageCreatedResponse;
import org.spaghetti.chatbackend.model.Chat;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class MessageService {
    private final UserService userService;
    private final MessageDtoConverter messageDtoConverter;
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    public MessageService(UserService userService, MessageDtoConverter messageDtoConverter, MessageRepository messageRepository, ChatService chatService) {
        this.userService = userService;
        this.messageDtoConverter = messageDtoConverter;
        this.messageRepository = messageRepository;
        this.chatService = chatService;
    }

    public MessageCreatedResponse saveMessage(CreateMessageRequest createMessageRequest) {
        Chat chat = chatService.findChatById(createMessageRequest.getChatId());
        User user = userService.getUserByNickname(createMessageRequest.getNickname());

        Message message = new Message();
        message.setMessage(createMessageRequest.getMessage());
        message.setTimeToReceived(LocalDateTime.now());
        message.setUser(user);
        message.setChat(chat);

        userService.addMessageToUser(user, message);
        chatService.addMessageToChat(chat, message);
        return messageDtoConverter.convertToMessageCreatedResponse(messageRepository.save(message));
    }
}

