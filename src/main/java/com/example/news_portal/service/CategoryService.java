package com.example.news_portal.service;

import com.example.news_portal.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Category updateCategory(Category newCategory);

    void deleteById(UUID id);

    Category findById(UUID id);


//    Category findByName(String name);


}
