package com.example.news_portal.service.impl;

import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.NewsRepository;
import com.example.news_portal.service.CategoryService;
import com.example.news_portal.service.NewsService;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final CategoryService categoryService;


    @Override
    public List<News> findAll(NewsFilter filter) {
        return newsRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();

    }


    @Override
    public News addNews(News news, UUID userId, UUID categoryId) {
        User author = userService.findById(userId);
        Category category = categoryService.findById(categoryId);

        author.addNews(news);
        category.addNews(news);
        news.setCategory(category);
        return newsRepository.save(news);
    }

    @Override
    public News updateNews(News news
            , UUID categoryId
    ) {
        if (categoryId != null) {
            Category category = categoryService.findById(categoryId);
            news.setCategory(category);
        }
        News updatedNews = newsRepository.findById(news.getId()).get();
        BeanUtils.copyProperties(news, updatedNews);
        return newsRepository.save(news);
    }

    @Override
    public void deleteById(UUID id) {
        News news = newsRepository.findById(id).get();
        User author = userService.findById(news.getAuthor().getId());
        author.deleteNews(news);
        newsRepository.deleteById(id);
    }

    @Override
    public News findById(UUID id) {
        return newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("News with suchId {0} not found!", id)));
    }

    @Override
    public List<News> findByCategoryName(String categoryName) {
        return newsRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<News> findByUsername(String username) {
        return newsRepository.findByAuthorUsername(username);
    }

}
