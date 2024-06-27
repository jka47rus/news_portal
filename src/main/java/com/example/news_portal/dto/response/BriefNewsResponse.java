package com.example.news_portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BriefNewsResponse {

    private UUID id;
    private String title;
    private String description;
    private String body;
    private String username;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer commentsCount;

}

