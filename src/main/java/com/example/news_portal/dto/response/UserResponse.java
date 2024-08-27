package com.example.news_portal.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String username;
    private String email;
}
