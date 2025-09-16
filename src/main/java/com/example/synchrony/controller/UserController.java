package com.example.synchrony.controller;

import com.example.synchrony.dto.RegisterRequest;
import com.example.synchrony.dto.UserResponse;
import com.example.synchrony.entity.UserAccount;
import com.example.synchrony.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService users;
    public UserController(UserService users) { this.users = users; }

    @PostMapping
    public UserResponse register(@Valid @RequestBody RegisterRequest req) {
        UserAccount ua = users.register(req);
        return new UserResponse(ua.getId(), ua.getUsername(), ua.getFullName());
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal UserAccount ua) {
        return new UserResponse(ua.getId(), ua.getUsername(), ua.getFullName());
    }
}
