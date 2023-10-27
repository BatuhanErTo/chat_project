package org.spaghetti.chatbackend.controller;

import org.spaghetti.chatbackend.dto.response.get.GetChatResponse;
import org.spaghetti.chatbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/getChatsByUser/{nickname}")
    public ResponseEntity<List<GetChatResponse>> getChatsByUserNickname(@PathVariable String nickname){
        return ResponseEntity.ok(userService.getChatsByUserNickname(nickname));
    }
}
