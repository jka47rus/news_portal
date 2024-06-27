package com.example.news_portal.mapper;

import com.example.news_portal.dto.request.UserRequest;
import com.example.news_portal.dto.response.UserListResponse;
import com.example.news_portal.dto.response.UserResponse;
import com.example.news_portal.model.User;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMap {

    private final UserService userService;

    public UserResponse userToResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }


    public User fromRequestToUser(UserRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }


    public User requestToUser(UUID id, UserRequest request) {
        if (id == null && request == null) {
            return null;
        }

        User user = userService.findById(id);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return user;
    }

    public List<UserResponse> userListToListResponse(List<User> users) {
        return users.stream().map(this::userToResponse).collect(Collectors.toList());
    }

    public UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUserList(userListToListResponse(users));
        return response;
    }
}

