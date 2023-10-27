package org.spaghetti.chatbackend.dto.converter;

import org.spaghetti.chatbackend.dto.ChatDto;
import org.spaghetti.chatbackend.dto.response.create.ChatCreatedResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.model.Chat;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ChatDtoConverter {
    private final MessageDtoMutualConverter messageDtoMutualConverter;

    public ChatDtoConverter(MessageDtoMutualConverter messageDtoMutualConverter) {
        this.messageDtoMutualConverter = messageDtoMutualConverter;
    }

    public ChatDto convertToChatDto(Chat chat){
        if (chat.getMessageList() == null){
            return new ChatDto(chat.getId(), null);
        }
        return new ChatDto(chat.getId(),
                chat.getMessageList().stream()
                        .map(messageDtoMutualConverter::convertToMessageDtoMutual)
                        .collect(Collectors.toList()));
    }

    public ChatCreatedResponse convertToChatCreatedResponse(Chat chat){
        return new ChatCreatedResponse(chat.getId());
    }

    public GetChatResponse convertToGetChatResponse(Chat chat){
        return new GetChatResponse(chat.getId());
    }
}
