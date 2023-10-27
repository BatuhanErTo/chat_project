package org.spaghetti.chatbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spaghetti.chatbackend.dto.converter.MessageDtoConverter;
import org.spaghetti.chatbackend.dto.request.CreateMessageRequest;
import org.spaghetti.chatbackend.dto.response.create.MessageCreatedResponse;
import org.spaghetti.chatbackend.exception.ChatNotFoundException;
import org.spaghetti.chatbackend.exception.UserNotFoundException;
import org.spaghetti.chatbackend.model.Chat;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.MessageRepository;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class MessageServiceTest {
    private UserService userService;
    private MessageDtoConverter messageDtoConverter;
    private MessageRepository messageRepository;
    private ChatService chatService;
    private MessageService messageService;

    @BeforeEach
    void setUp(){
        userService = mock(UserService.class);
        messageDtoConverter = mock(MessageDtoConverter.class);
        messageRepository = mock(MessageRepository.class);
        chatService = mock(ChatService.class);
        messageService = new MessageService(userService, messageDtoConverter, messageRepository,chatService);
    }

    @Test
    void saveMessage_WhenChatByIdNotExist_ShouldThrowChatNotFoundException() {
        when(chatService.findChatById(Mockito.any())).thenThrow(new ChatNotFoundException("Chat not found!"));
        assertThrows(ChatNotFoundException.class, () -> messageService.saveMessage(new CreateMessageRequest(
                "hi test",
                "BatuTest",
                1L)));
    }

    @Test
    void saveMessage_WhenNicknameNotExist_ShouldThrowUserNotFoundException() {
        when(userService.getUserByNickname(Mockito.any())).thenThrow(new UserNotFoundException("User not found!"));
        assertThrows(UserNotFoundException.class,() -> messageService.saveMessage(
                new CreateMessageRequest(
                        "hi test",
                        "BatuTest",
                        1L)));
    }

    @Test
    public void saveMessage_WhenChatIdAndNicknameExist_ShouldReturnMessageCreatedResponse() {
        CreateMessageRequest createMessageRequest = new CreateMessageRequest();
        createMessageRequest.setChatId(1L);
        createMessageRequest.setNickname("testUser");
        createMessageRequest.setMessage("test");

        Chat chat = new Chat();
        chat.setId(1L);

        User user = new User();
        user.setUsername("testUser");

        Message message = new Message();
        message.setMessage("test");
        message.setTimeToReceived(LocalDateTime.now());
        message.setUser(user);
        message.setChat(chat);

        MessageCreatedResponse expectedResponse = new MessageCreatedResponse();
        expectedResponse.setNickname("testUser");
        expectedResponse.setChatId(1L);
        expectedResponse.setMessage("test");

        when(chatService.findChatById(1L)).thenReturn(chat);
        when(userService.getUserByNickname("testUser")).thenReturn(user);
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageDtoConverter.convertToMessageCreatedResponse(message)).thenReturn(expectedResponse);


        MessageCreatedResponse actualResponse = messageService.saveMessage(createMessageRequest);


        assertEquals(expectedResponse.getChatId(), actualResponse.getChatId());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());

        verify(userService, times(1)).getUserByNickname("testUser");
        verify(chatService, times(1)).findChatById(1L);

        verify(userService,times(1)).addMessageToUser(
                argThat(argument -> argument.getUsername().equals("testUser")),
                argThat(argument -> argument.getMessage().equals("test")));
        verify(chatService,times(1)).addMessageToChat(
                argThat(argument -> argument.getId() == 1L),
                argThat(argument -> argument.getMessage().equals("test"))
        );
    }
}
