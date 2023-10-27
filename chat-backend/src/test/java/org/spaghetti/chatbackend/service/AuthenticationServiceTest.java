package org.spaghetti.chatbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spaghetti.chatbackend.dto.converter.UserDtoConverter;
import org.spaghetti.chatbackend.dto.request.CreateUserRequest;
import org.spaghetti.chatbackend.dto.response.create.UserRegisteredResponse;
import org.spaghetti.chatbackend.exception.RoleNotFoundException;
import org.spaghetti.chatbackend.exception.UserAlreadyExistException;
import org.spaghetti.chatbackend.model.Role;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AuthenticationServiceTest {
    private UserService userService;
    private RoleRepository roleRepository;
    private UserDtoConverter userDtoConverter;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp(){
        userService = mock(UserService.class);
        roleRepository = mock(RoleRepository.class);
        userDtoConverter = mock(UserDtoConverter.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        tokenService = mock(TokenService.class);
        authenticationService = new AuthenticationService(userService, roleRepository,userDtoConverter,
                passwordEncoder,
                authenticationManager,
                tokenService);
    }


    @Test
    void registerUser_WhenUserAlreadyExistAndRoleExist_ShouldReturnRegisteredUserResponse(){
        CreateUserRequest createUserRequest = new CreateUserRequest("testUser","123");
        when(userService.isUserExistByName(createUserRequest.getUsername()))
                .thenReturn(false);
        Role role = new Role(1L,"USER");
        when(roleRepository.findByAuthority("USER")).thenReturn(Optional.of(role));
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        String passwordEncoded = "$2y$10$JMMK5fPmYU2mud71i96IeuadncuhzQgIFBcVAlNGqYj5K5FEGe8zy";
        when(passwordEncoder.encode(passwordEncoded)).thenReturn(anyString());
        User user = new User(1L, createUserRequest.getUsername(), passwordEncoded,authorities,null);
        when(userService.createUser(createUserRequest.getUsername(),anyString(),authorities))
                .thenReturn(user);
        UserRegisteredResponse expected = new UserRegisteredResponse(createUserRequest.getUsername());
        when(userDtoConverter.convertToUserRegisteredResponse(user)).thenReturn(expected);
        UserRegisteredResponse actual = authenticationService.registerUser(createUserRequest);
        assertEquals(expected.getUsername(),actual.getUsername());
    }

    @Test
    void registerUser_WhenUserAlreadyExistByName_ThrowUserAlreadyExistException(){
        when(userService.isUserExistByName(Mockito.anyString())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class,
                () -> authenticationService.registerUser(new CreateUserRequest("batu","1234")));
    }

    @Test
    void registerUser_WhenRoleNotExist_ShouldThrowRoleNotFoundException(){
        when(userService.isUserExistByName(Mockito.anyString())).thenReturn(false);
        when(roleRepository.findByAuthority(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(RoleNotFoundException.class,
                () -> authenticationService.registerUser(new CreateUserRequest("batu","1234")));
    }

    @Test
    @Disabled
    void loginUser_WhenCredentialsNotAuthenticated_ShouldThrowAuthenticationServiceException(){
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(AuthenticationException.class);
        assertThrows(RuntimeException.class,
                () -> authenticationService.loginUser("batu","123"));
    }

}
