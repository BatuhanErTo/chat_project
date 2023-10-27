package org.spaghetti.chatbackend.service;


import org.spaghetti.chatbackend.dto.converter.ChatDtoConverter;
import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.exception.UserNotFoundException;
import org.spaghetti.chatbackend.model.Chat;
import org.spaghetti.chatbackend.model.Message;
import org.spaghetti.chatbackend.model.Role;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final ChatDtoConverter chatDtoConverter;
    private final UserRepository userRepository;

    public UserService(ChatDtoConverter chatDtoConverter, UserRepository userRepository) {
        this.chatDtoConverter = chatDtoConverter;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not valid"));
    }
    protected User createUser(String username, String password, Set<Role> authorities){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }
    protected void addMessageToUser(User user, Message message) {
        List<Message> messageList = user.getMessageList();
        if (messageList == null) {
            user.setMessageList(List.of(message));
            return;
        }
        messageList.add(message);
        user.setMessageList(messageList);
    }
    protected boolean isUserExistByName(String username){
        return userRepository.findByUsername(username).isPresent();
    }
    protected User getUserByNickname(String nickname) {
        return userRepository.findByUsername(nickname)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public List<GetChatResponse> getChatsByUserNickname(String nickname) {
        User user = getUserByNickname(nickname);
        List<Chat> chatList = user.getMessageList().stream()
                .map(Message::getChat)
                .distinct()
                .toList();
        return chatList.stream().map(chatDtoConverter::convertToGetChatResponse).collect(Collectors.toList());
    }
}
