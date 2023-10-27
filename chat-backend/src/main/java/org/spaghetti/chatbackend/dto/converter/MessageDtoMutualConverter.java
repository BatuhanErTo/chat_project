package org.spaghetti.chatbackend.dto.converter;

import org.spaghetti.chatbackend.dto.MessageDtoMutual;
import org.spaghetti.chatbackend.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoMutualConverter {
    public MessageDtoMutual convertToMessageDtoMutual(Message message) {
        return new MessageDtoMutual(message.getId(),
                message.getMessage(),
                message.getTimeToReceived());
    }
}
