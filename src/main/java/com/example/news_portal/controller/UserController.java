package com.example.news_portal.controller;

import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.dto.request.UserRequest;
import com.example.news_portal.dto.response.UserListResponse;
import com.example.news_portal.dto.response.UserResponse;
import com.example.news_portal.exception.AlreadyExistsException;
import com.example.news_portal.mapper.UserMap;
import com.example.news_portal.model.RoleType;
import com.example.news_portal.model.User;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMap userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(userMapper.userListToUserListResponse(userService.findAll(filter)));
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request,
                                                   @RequestParam(value = "role") RoleType role
    ) {
        if (userService.existByEmail(request.getEmail())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("User with email {0} already exists!", request.getEmail()));
        }
        if (userService.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException(MessageFormat
                    .format("User with username {0} already exists!", request.getUsername()));
        }

        User newUser = userMapper.fromRequestToUser(request);
        newUser.setRole(role);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(userService.save(newUser)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request, @PathVariable UUID id) {
        User updatedUser = userService.update(userMapper.requestToUser(id, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

}
