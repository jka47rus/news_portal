package com.example.news_portal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {

    private UUID id;
    @NotNull
    private String name;
}
