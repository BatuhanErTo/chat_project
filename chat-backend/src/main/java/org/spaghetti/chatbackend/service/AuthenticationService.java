package org.spaghetti.chatbackend.service;

import org.spaghetti.chatbackend.dto.converter.UserDtoConverter;
import org.spaghetti.chatbackend.dto.request.CreateUserRequest;
import org.spaghetti.chatbackend.dto.response.LoginResponse;
import org.spaghetti.chatbackend.dto.response.create.UserRegisteredResponse;
import org.spaghetti.chatbackend.exception.RoleNotFoundException;
import org.spaghetti.chatbackend.exception.UserAlreadyExistException;
import org.spaghetti.chatbackend.model.Role;
import org.spaghetti.chatbackend.model.User;
import org.spaghetti.chatbackend.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class AuthenticationService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserDtoConverter userDtoConverter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(UserService userService, RoleRepository roleRepository,
                                 UserDtoConverter userDtoConverter,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenService tokenService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userDtoConverter = userDtoConverter;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public UserRegisteredResponse registerUser(CreateUserRequest createUserRequest){
        if (userService.isUserExistByName(createUserRequest.getUsername())){
            throw new UserAlreadyExistException("User with this nickname is already exist!");
        }
        Role role = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role does not exist!"));
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        User user = userService.createUser(createUserRequest.getUsername(),
                passwordEncoder.encode(createUserRequest.getPassword()),
                authorities);
        return userDtoConverter.convertToUserRegisteredResponse(user);
    }

    public LoginResponse loginUser(String username, String password){
        try {
            Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(username,password));
            String token = tokenService.generateJwt(authentication);
            return new LoginResponse(username,token);
        }catch (AuthenticationException exception){
            throw new AuthenticationServiceException("Invalid credentials!");
        }
    }
}
