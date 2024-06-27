package com.example.news_portal.repository;

import com.example.news_portal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {


//    Page<News> findAllByCategoryId(UUID categoryId);
//    Page<News> findAllByAuthorId(UUID userId, Pageable pageable);
//    boolean existByIdAuthorId(UUID id, UUID authorId);
}
