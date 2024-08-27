package com.example.news_portal.controller;

import com.example.news_portal.aop.Loggable;
import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.dto.request.UserRequest;
import com.example.news_portal.dto.response.UserListResponse;
import com.example.news_portal.dto.response.UserResponse;
import com.example.news_portal.exception.AlreadyExistsException;
import com.example.news_portal.mapper.UserMap;
import com.example.news_portal.model.Role;
import com.example.news_portal.model.RoleType;
import com.example.news_portal.model.User;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(userMapper.userListToUserListResponse(userService.findAll(filter)));
    }


    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request,
                                                   @RequestParam(value = "role") RoleType roleType
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(userService.save(newUser, Role.from(roleType))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Loggable
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody UserRequest request) {

        User updatedUser = userService.update(userMapper.requestToUser(id, request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Loggable
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Loggable
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

}
