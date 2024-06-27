package com.example.news_portal.repository;

import com.example.news_portal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
//    Optional<Category> findByName(String name);
}
