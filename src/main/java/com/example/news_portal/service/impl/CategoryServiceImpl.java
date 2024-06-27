package com.example.news_portal.service.impl;

import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Category;
import com.example.news_portal.repository.CategoryRepository;
import com.example.news_portal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        BeanUtils.copyProperties(category, updatedCategory);
        return categoryRepository.save(category);

    }

    @Override
    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Category with ID {0} not found", id)));
    }


//    @Override
//    public Category findByName(String name) {
//        return categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(MessageFormat
//                .format("Category with name {0} not found", name)));
//    }

}
