package org.spaghetti.chatbackend.dto.converter;

import org.spaghetti.chatbackend.dto.MessageDto;
import org.spaghetti.chatbackend.dto.response.create.MessageCreatedResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatHistoryByChatIdResponse;
import org.spaghetti.chatbackend.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoConverter {
    private final UserDtoConverter userDtoConverter;
    private final ChatDtoConverter chatDtoConverter;

    public MessageDtoConverter(UserDtoConverter userDtoConverter, ChatDtoConverter chatDtoConverter) {
        this.userDtoConverter = userDtoConverter;
        this.chatDtoConverter = chatDtoConverter;
    }

    public MessageDto convertToMessageDto(Message message) {
        return new MessageDto(message.getId(),
                message.getMessage(),
                message.getTimeToReceived(),
                userDtoConverter.convertToUserDto(message.getUser()),
                chatDtoConverter.convertToChatDto(message.getChat()));
    }

    public MessageCreatedResponse convertToMessageCreatedResponse(Message message){
        return new MessageCreatedResponse(message.getUser().getUsername(),
                message.getMessage(),
                message.getChat().getId());
    }

    public GetChatHistoryByChatIdResponse convertToGetChatHistory(Message message) {
        return new GetChatHistoryByChatIdResponse(message.getMessage(),message.getUser().getUsername(),message.getTimeToReceived());
    }
}
