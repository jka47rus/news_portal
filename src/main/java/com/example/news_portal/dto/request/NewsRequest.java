package com.example.news_portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {
    private UUID id;
    private String title;
    private String description;
    private String body;
}
