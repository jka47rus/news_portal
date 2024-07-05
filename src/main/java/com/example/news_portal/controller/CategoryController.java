package com.example.news_portal.controller;

import com.example.news_portal.dto.request.CategoryRequest;
import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.dto.response.CategoryListResponse;
import com.example.news_portal.dto.response.CategoryResponse;
import com.example.news_portal.mapper.CategoryMap;
import com.example.news_portal.model.Category;
import com.example.news_portal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMap categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryListResponse(categoryService.findAll(filter)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        Category category = categoryService.save(categoryMapper.fromRequestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest request,
                                                           @PathVariable UUID id) {
        Category category = categoryService.updateCategory(categoryMapper.requestToCategory(id, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryService.findById(id)));
    }


}
