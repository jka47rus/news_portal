package com.example.news_portal.service;

import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.model.News;

import java.util.List;
import java.util.UUID;

public interface NewsService {

    List<News> findByUsername(String username);

    List<News> findAll(NewsFilter filter);

    News addNews(News news, UUID userId, UUID categoryId);

    News updateNews(News news, UUID categoryId
    );

    void deleteById(UUID id);

    News findById(UUID newsId);

    List<News> findByCategoryName(String categoryName);

}
