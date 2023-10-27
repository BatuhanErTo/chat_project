package org.spaghetti.chatbackend.service;


import org.spaghetti.chatbackend.dto.converter.ChatDtoConverter;
import org.spaghetti.chatbackend.dto.converter.MessageDtoConverter;
import org.spaghetti.chatbackend.dto.response.create.ChatCreatedResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatHistoryByChatIdResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.exception.ChatNotFoundException;
import org.spaghetti.chatbackend.model.Chat;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatDtoConverter chatDtoConverter;
    private final MessageDtoConverter messageDtoConverter;

    public ChatService(ChatRepository chatRepository, ChatDtoConverter chatDtoConverter, MessageDtoConverter messageDtoConverter) {
        this.chatRepository = chatRepository;
        this.chatDtoConverter = chatDtoConverter;
        this.messageDtoConverter = messageDtoConverter;
    }

    public ChatCreatedResponse createChat(){
        return chatDtoConverter.convertToChatCreatedResponse(chatRepository.save(new Chat()));
    }

    protected Chat findChatById(Long id){
        return chatRepository.findChatById(id).orElseThrow(() -> new ChatNotFoundException("Chat not found!"));
    }

    protected void addMessageToChat(Chat chat, Message message) {
        List<Message> messageList = chat.getMessageList();
        if (messageList == null){
            chat.setMessageList(List.of(message));
            return;
        }
        messageList.add(message);
        chat.setMessageList(messageList);
    }

    public List<GetChatResponse> getAllChat() {
        return chatRepository.findAll().stream().map(chatDtoConverter::convertToGetChatResponse)
                .collect(Collectors.toList());
    }

    public List<GetChatHistoryByChatIdResponse> getChatHistoryByChatId(Long chatId) {
        Chat chat = findChatById(chatId);
        return chat.getMessageList().stream()
                .map(messageDtoConverter::convertToGetChatHistory)
                .collect(Collectors.toList());
    }
}
