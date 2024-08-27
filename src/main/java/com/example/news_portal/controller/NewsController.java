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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findAll(filter)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id)));
    }

    @PostMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> createNews(@PathVariable UUID categoryId,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody NewsRequest request) {

        News news = newsService.addNews(newsMapper.fromRequestToNews(request), userDetails.getUsername(), categoryId);

        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(news));
    }

    @PutMapping("/{id}")
    @Loggable
    public ResponseEntity<NewsResponse> updateNews(@PathVariable UUID id,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody NewsRequest request,
                                                   @RequestParam(required = false) UUID categoryId) {
        News news = newsService.updateNews(newsMapper.requestToNews(id, request), categoryId);

        return ResponseEntity.ok(newsMapper.newsToResponse(news));

    }

    @DeleteMapping("/{id}")
    @Loggable
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteNews(@PathVariable UUID id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
