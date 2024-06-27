package com.example.news_portal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private UUID id;
    private String comment;
    private String username;
    private Instant createdAt;
    private Instant updatedAt;
}
