package com.example.news_portal.repository;

import com.example.news_portal.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {

    List<News> findByCategoryName(String categoryName);

    List<News> findByAuthorUsername(String username);

    Page<News> findAll(Pageable pageable);
}
