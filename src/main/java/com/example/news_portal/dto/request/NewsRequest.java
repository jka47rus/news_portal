package com.example.news_portal.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class NewsRequest {
    private UUID id;
    private String title;
    private String description;
    private String body;
}
