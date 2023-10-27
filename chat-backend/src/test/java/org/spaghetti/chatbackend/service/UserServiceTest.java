package org.spaghetti.chatbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.spaghetti.chatbackend.dto.converter.ChatDtoConverter;
import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.exception.UserNotFoundException;
import org.spaghetti.chatbackend.model.Chat;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.model.Role;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private ChatDtoConverter chatDtoConverter;
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp(){
        chatDtoConverter = mock(ChatDtoConverter.class);
        userRepository = mock(UserRepository.class);
        userService = new UserService(chatDtoConverter,userRepository);
    }

    @Test
    void loadUserByUserName_WhenUsernameExist_ShouldReturnUserDetails(){
        String username = "batu";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        UserDetails result = userService.loadUserByUsername(username);
        assertEquals(user,result);
    }

    @Test
    void loadUserByUserName_WhenUsernameNotExist_ShouldThrowUsernameNotFoundException(){
        when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,() -> userService.loadUserByUsername("Batu"));
    }

    @Test
    void createUser_ShouldSaveUser(){
       when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
       User user = userService.createUser("batuhan","1234", Set.of(new Role(1L,"USER")));
       assertNotNull(user);
       verify(userRepository).save(argThat(argument -> argument.getUsername().equals("batuhan")));
    }

    @Test
    void addMessageToUser_WhenMessageListAlreadyExist_ShouldAddMessageToUser(){
        User user = mock(User.class);
        Message messageToAdd = new Message(3L,"test",LocalDateTime.now(),user,null);
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(1L,"test", LocalDateTime.now(),user,null));
        messageList.add(new Message(2L,"test",LocalDateTime.now(),user,null));
        when(user.getMessageList()).thenReturn(messageList);
        userService.addMessageToUser(user,messageToAdd);
        assertTrue(messageList.contains(messageToAdd));
        verify(user).setMessageList(messageList);
    }

    @Test
    void addMessageToUser_WhenMessageListIsNull_ShouldAddMessageToUser(){
        User user = new User();
        Message messageToAdd = new Message(1L,"test",LocalDateTime.now(),user,null);
        userService.addMessageToUser(user,messageToAdd);
        List<Message> messageList = user.getMessageList();
        assertEquals(messageList.size(),1);
        assertTrue(messageList.contains(messageToAdd));
    }

    @Test
    void isUserExistByName_WhenUsernameExist_ShouldReturnTrue(){
        String username = "Batuhan";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));
        boolean result = userService.isUserExistByName(username);
        assertTrue(result);
    }

    @Test
    void isUserExistByName_WhenUsernameNotExist_ShouldReturnFalse(){
        String username = "Batuhan";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        boolean result = userService.isUserExistByName(username);
        assertFalse(result);
    }

    @Test
    void getUserByNickname_WhenNicknameExist_ShouldReturnUser(){
        String nickname = "Batuhan";
        User user = new User();
        user.setUsername(nickname);
        when(userRepository.findByUsername(nickname))
                .thenReturn(Optional.of(user));
        User result = userService.getUserByNickname(nickname);
        assertEquals(user.getUsername(),result.getUsername());
        assertEquals(user,result);
    }

    @Test
    void getUserByNickname_WhenNicknameNotExist_ShouldThrowUserNotFoundException(){
        String nickname = "Batuhan";
        when(userRepository.findByUsername(nickname))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.getChatsByUserNickname(nickname));
    }

    @Test
    void getChatsByUserNickname_WhenNicknameExist_ShouldReturnListOfGetChatResponse(){
        String username = "Batuhan";
        User user = new User();

        Chat chat1 = new Chat();
        chat1.setId(1L);
        Chat chat2 = new Chat();
        chat2.setId(2L);

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(1L,"test1", LocalDateTime.now(),user,chat1));
        messageList.add(new Message(2L,"test2",LocalDateTime.now(),user,chat2));
        user.setMessageList(messageList);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        when(chatDtoConverter.convertToGetChatResponse(Mockito.any(Chat.class)))
                .thenReturn(new GetChatResponse());

        List<GetChatResponse> result = userService.getChatsByUserNickname(username);
        assertEquals(2,result.size());
    }

    @Test
    void getChatsByUserNickname_WhenNicknameNotExist_ShouldThrowUserNotFoundException(){
        String username = "Batuhan";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> userService.getChatsByUserNickname(username));
    }

}
