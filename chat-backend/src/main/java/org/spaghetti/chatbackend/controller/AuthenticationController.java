package org.spaghetti.chatbackend.controller;

import org.spaghetti.chatbackend.dto.request.CreateUserRequest;
import org.spaghetti.chatbackend.dto.request.LoginRequest;
import org.spaghetti.chatbackend.dto.response.LoginResponse;
import org.spaghetti.chatbackend.dto.response.create.UserRegisteredResponse;
import org.spaghetti.chatbackend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisteredResponse> registerUser(@RequestBody CreateUserRequest createUserRequest){
        return ResponseEntity.ok(authenticationService.registerUser(createUserRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.loginUser(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
