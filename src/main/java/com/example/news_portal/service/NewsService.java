package com.example.news_portal.service;

import com.example.news_portal.model.News;

import java.util.List;
import java.util.UUID;

public interface NewsService {
    //    News findById(UUID id);
    List<News> findAll();

    News addNews(News news, UUID userId, UUID categoryId);

    News updateNews(News news //, UUID categoryId
    );

    void deleteById(UUID id);

    News findById(UUID newsId);


//    News save(News news);
//    Page<News> findAllByCategoryId(UUID categoryId);
//    Page<News> findAllByAuthorId(UUID userId, Pageable pageable);
//    boolean existByIdAuthorId(UUID id, UUID authorId);


}
