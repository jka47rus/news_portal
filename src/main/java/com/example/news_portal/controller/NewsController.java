package com.example.news_portal.controller;

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

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findAll()));
    }

    @PostMapping("/{userId}/{categoryId}")
    public ResponseEntity<NewsResponse> createNews(@RequestBody NewsRequest request,
                                                   @PathVariable UUID userId,
                                                   @PathVariable UUID categoryId) {

        News news = newsService.addNews(newsMapper.fromRequestToNews(request), userId, categoryId);

        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(news));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> updateNews(@RequestBody NewsRequest request,
                                                   @PathVariable UUID id
//            ,                                      @RequestParam(required = false) UUID categoryId
    ) {
        News news = newsService.updateNews(newsMapper.requestToNews(id, request)
//                , categoryId
        );

        return ResponseEntity.ok(newsMapper.newsToResponse(news));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

}
