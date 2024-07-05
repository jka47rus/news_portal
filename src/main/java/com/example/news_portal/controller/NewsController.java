package com.example.news_portal.controller;

import com.example.news_portal.aop.Loggable;
import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.dto.request.NewsRequest;
import com.example.news_portal.dto.response.NewsListResponse;
import com.example.news_portal.dto.response.NewsResponse;
import com.example.news_portal.mapper.NewsMap;
import com.example.news_portal.model.News;
import com.example.news_portal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final NewsMap newsMapper;

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> findByCategoryOrUserName(@RequestParam(required = false) String categoryName,
                                                                     @RequestParam(required = false) String username) {
        if (categoryName != null) {
            return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findByCategoryName(categoryName)));
        }
        if (username != null) {
            return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findByUsername(username)));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findAll(filter)));
    }

    @PostMapping("/{userId}/{categoryId}")
    public ResponseEntity<NewsResponse> createNews(@RequestBody NewsRequest request,
                                                   @PathVariable UUID userId,
                                                   @PathVariable UUID categoryId) {

        News news = newsService.addNews(newsMapper.fromRequestToNews(request), userId, categoryId);

        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(news));
    }

    @PutMapping("/{id}")
    @Loggable
    public ResponseEntity<NewsResponse> updateNews(@RequestParam UUID userId,
                                                   @PathVariable UUID id,
                                                   @RequestBody NewsRequest request,
                                                   @RequestParam(required = false) UUID categoryId) {
        News news = newsService.updateNews(newsMapper.requestToNews(id, request), categoryId);

        return ResponseEntity.ok(newsMapper.newsToResponse(news));

    }

    @DeleteMapping("/{id}")
    @Loggable
    public ResponseEntity<Void> deleteNews(@RequestParam UUID userId, @PathVariable UUID id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

}
