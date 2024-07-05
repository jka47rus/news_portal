package com.example.news_portal.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private UUID id;
    private String username;
    private String email;
    private String password;
}
