package com.example.news_portal.dto.request;

import lombok.Data;

@Data
public class NewsFilter {
    private Integer pageSize;
    private Integer pageNumber;
}
