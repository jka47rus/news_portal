package com.example.news_portal.mapper;

import com.example.news_portal.dto.request.CategoryRequest;
import com.example.news_portal.dto.response.CategoryListResponse;
import com.example.news_portal.dto.response.CategoryResponse;
import com.example.news_portal.model.Category;
import com.example.news_portal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMap {

    private final CategoryService categoryService;


    public List<CategoryResponse> categoryListToListResponse(List<Category> categories) {
        return categories.stream().map(this::categoryToResponse).collect(Collectors.toList());
    }

    public CategoryListResponse categoryListToCategoryListResponse(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategoryList(categoryListToListResponse(categories));
        return response;
    }

    public CategoryResponse categoryToResponse(Category category) {
        if (category == null) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());

        return categoryResponse;
    }


    public Category fromRequestToCategory(CategoryRequest request) {
        if (request == null) {
            return null;
        }

        Category category = new Category();

        category.setId(request.getId());
        category.setName(request.getName());

        return category;
    }

    public Category requestToCategory(UUID id, CategoryRequest request) {
        if (id == null && request == null) {
            return null;
        }
        Category category = categoryService.findById(id);
        category.setName(request.getName());
        return category;
    }

}
