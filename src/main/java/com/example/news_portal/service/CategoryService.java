package com.example.news_portal.service;

import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll(NewsFilter filter);

    Category save(Category category);

    Category updateCategory(Category newCategory);

    void deleteById(UUID id);

    Category findById(UUID id);

}
