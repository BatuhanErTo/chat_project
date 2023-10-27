package org.spaghetti.chatbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spaghetti.chatbackend.dto.converter.ChatDtoConverter;
import org.spaghetti.chatbackend.dto.converter.MessageDtoConverter;
import org.spaghetti.chatbackend.dto.response.get.GetChatHistoryByChatIdResponse;
import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.exception.ChatNotFoundException;
import org.spaghetti.chatbackend.model.Chat;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.ChatRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChatServiceTest {
    private ChatRepository chatRepository;
    private ChatDtoConverter chatDtoConverter;
    private MessageDtoConverter messageDtoConverter;

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatRepository = mock(ChatRepository.class);
        chatDtoConverter = mock(ChatDtoConverter.class);
        messageDtoConverter = mock(MessageDtoConverter.class);
        chatService = new ChatService(chatRepository, chatDtoConverter, messageDtoConverter);
    }

    @Test
    void findChatId_whenChatIdExist_shouldReturnChat() {
        Chat chat = new Chat(1L, List.of(new Message(1L,"hello", LocalDateTime.now(),
                new User(1L, "batu", null, null, null),
                null)));
        when(chatRepository.findChatById(1L)).thenReturn(Optional.of(chat));
        Chat result = chatService.findChatById(1L);
        assertEquals(chat, result);
    }

    @Test
    void findChatId_whenChatIdNotExist_shouldThrowChatNotFoundException() {
        when(chatRepository.findChatById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ChatNotFoundException.class, () -> chatService.findChatById(1L));
    }

    @Test
    void addMessageToChat_whenMessageListAlreadyExist() {
        Chat chat = Mockito.mock(Chat.class);

        Message message1 = new Message(1L,"Test1", LocalDateTime.now(),
                new User(), chat);
        Message message2 = new Message(2L,"Test2", LocalDateTime.now(),
                new User(), chat);
        Message message3 = new Message(3L,"Test2", LocalDateTime.now(),
                new User(), chat);

        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);

        when(chat.getMessageList()).thenReturn(messageList);
        chatService.addMessageToChat(chat, message3);

        assertTrue(messageList.contains(message3));
        Mockito.verify(chat).setMessageList(messageList);
    }

    @Test
    void addMessageToChat_whenMessageListNull() {
        Chat chat = new Chat();
        Message message = new Message(1L,"Test1", LocalDateTime.now(),
                new User(), chat);

        chatService.addMessageToChat(chat, message);
        List<Message> messageList = chat.getMessageList();

        assertNotNull(messageList);
        assertEquals(1, messageList.size());
        assertEquals(message, messageList.get(0));
    }

    @Test
    void getAllChat_whenChatListExist_shouldReturnListOfGetChatResponse() {
        List<Chat> chatList = Arrays.asList(new Chat(), new Chat());
        when(chatRepository.findAll()).thenReturn(chatList);

        when(chatDtoConverter.convertToGetChatResponse(new Chat()))
                .thenReturn(new GetChatResponse());
        List<GetChatResponse> expectedResult = chatList.stream()
                .map(chatDtoConverter::convertToGetChatResponse)
                .toList();

        List<GetChatResponse> actualResult = chatService.getAllChat();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllChat_whenChatListEmpty_shouldReturnEmptyListOfGetChatResponse() {
        when(chatRepository.findAll()).thenReturn(Collections.emptyList());
        when(chatDtoConverter.convertToGetChatResponse(new Chat()))
                .thenReturn(new GetChatResponse());
        List<GetChatResponse> result = chatService.getAllChat();
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getChatHistoryByChatId_whenChatIdExist_shouldReturnGetChatHistoryByChatIdResponse() {
        Chat chat = Mockito.mock(Chat.class);
        when(chatRepository.findChatById(Mockito.any())).thenReturn(Optional.ofNullable(chat));
        List<Message> messageList = Arrays.asList(new Message(1L,"Test1", LocalDateTime.now(),
                        new User(),
                        chat)
                , new Message(2L,"Test2", LocalDateTime.now(),
                        new User(),
                        chat));
        assert chat != null;
        when(chat.getMessageList()).thenReturn(messageList);
        when(messageDtoConverter.convertToGetChatHistory(new Message()))
                .thenReturn(new GetChatHistoryByChatIdResponse());
        List<GetChatHistoryByChatIdResponse> expectedResult = messageList.stream()
                .map(messageDtoConverter::convertToGetChatHistory)
                .toList();
        List<GetChatHistoryByChatIdResponse> actualResult = chatService.getChatHistoryByChatId(1L);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getChatHistoryByChatId_whenChatIdExist_shouldReturnEmptyGetChatHistoryByChatIdResponse(){
        Chat chat = Mockito.mock(Chat.class);
        when(chatRepository.findChatById(Mockito.any())).thenReturn(Optional.ofNullable(chat));
        List<Message> messageList = Collections.emptyList();
        assert chat != null;
        when(chat.getMessageList()).thenReturn(messageList);
        when(messageDtoConverter.convertToGetChatHistory(new Message()))
                .thenReturn(new GetChatHistoryByChatIdResponse());
        List<GetChatHistoryByChatIdResponse> actualResult = chatService.getChatHistoryByChatId(1L);
        assertEquals(Collections.emptyList(),actualResult);
    }

    @Test
    void getChatHistoryByChatId_whenChatIdNotExist_shouldThrowChatNotFoundException(){
        when(chatRepository.findChatById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ChatNotFoundException.class, () -> chatService.getChatHistoryByChatId(1L));
    }

}
