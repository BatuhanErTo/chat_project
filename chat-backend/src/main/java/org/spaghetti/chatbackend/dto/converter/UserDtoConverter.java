package org.spaghetti.chatbackend.dto.converter;

import org.spaghetti.chatbackend.dto.UserDto;
import org.spaghetti.chatbackend.dto.response.create.UserRegisteredResponse;
import org.spaghetti.chatbackend.dto.response.get.GetByUserResponse;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {
    private final MessageDtoMutualConverter messageDtoMutualConverter;

    public UserDtoConverter(MessageDtoMutualConverter messageDtoMutualConverter) {
        this.messageDtoMutualConverter = messageDtoMutualConverter;
    }

    public UserDto convertToUserDto(User user) {
        if (user.getMessageList() == null) {
            return new UserDto(user.getUserId(),
                    user.getUsername(),
                    null);
        }
        return new UserDto(user.getUserId(),
                user.getUsername(),
                user.getMessageList().stream()
                        .map(messageDtoMutualConverter::convertToMessageDtoMutual)
                        .collect(Collectors.toList()));
    }

    public UserRegisteredResponse convertToUserRegisteredResponse(User user){
        return new UserRegisteredResponse(user.getUsername());
    }


}
