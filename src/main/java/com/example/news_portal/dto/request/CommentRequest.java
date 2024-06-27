package com.example.news_portal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentRequest {

    private UUID id;
    @NotNull
    private String comment;
}
