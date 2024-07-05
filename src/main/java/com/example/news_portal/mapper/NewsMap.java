package com.example.news_portal.mapper;

import com.example.news_portal.dto.request.NewsRequest;
import com.example.news_portal.dto.response.BriefNewsResponse;
import com.example.news_portal.dto.response.CommentResponse;
import com.example.news_portal.dto.response.NewsListResponse;
import com.example.news_portal.dto.response.NewsResponse;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import com.example.news_portal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMap {


    private final NewsService newsService;

    public News fromRequestToNews(NewsRequest request) {
        if (request == null) {
            return null;
        }

        News news = new News();
        news.setId(request.getId());
        news.setTitle(request.getTitle());
        news.setDescription(request.getDescription());
        news.setBody(request.getBody());

        return news;
    }

    public News requestToNews(UUID id, NewsRequest request) {
        if (id == null && request == null) {
            return null;
        }

        News news = newsService.findById(id);
        news.setTitle(request.getTitle());
        news.setDescription(request.getDescription());
        news.setBody(request.getBody());

        return news;
    }


    public List<BriefNewsResponse> newsListToListResponse(List<News> news) {
        return news.stream().map(this::newsToBriefResponse).collect(Collectors.toList());
    }

    public NewsListResponse newsListToNewsListResponse(List<News> news) {
        NewsListResponse response = new NewsListResponse();
        response.setNewsList(newsListToListResponse(news));
        return response;
    }

    public NewsResponse newsToResponse(News news) {
        if (news == null) {
            return null;
        }

        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setUsername(news.getAuthor().getUsername());
        newsResponse.setComments(commentListToCommentResponseList(news.getComments()));

        newsResponse.setId(news.getId());
        newsResponse.setTitle(news.getTitle());
        newsResponse.setDescription(news.getDescription());
        newsResponse.setBody(news.getBody());
        newsResponse.setCreatedAt(news.getCreatedAt());
        newsResponse.setUpdatedAt(news.getUpdatedAt());


        return newsResponse;
    }

    protected List<CommentResponse> commentListToCommentResponseList(List<Comment> list) {
        if (list == null) {
            return null;
        }

        List<CommentResponse> commentResponses = new ArrayList<CommentResponse>(list.size());
        for (Comment comment : list) {
            commentResponses.add(commentToCommentResponse(comment));
        }

        return commentResponses;
    }

    protected CommentResponse commentToCommentResponse(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(comment.getId());
        commentResponse.setUsername(comment.getUser().getUsername());
        commentResponse.setComment(comment.getComment());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getUpdatedAt());

        return commentResponse;
    }

    public BriefNewsResponse newsToBriefResponse(News news) {
        if (news == null) {
            return null;
        }

        BriefNewsResponse briefNewsResponse = new BriefNewsResponse();

        briefNewsResponse.setUsername(news.getAuthor().getUsername());
        briefNewsResponse.setId(news.getId());
        briefNewsResponse.setTitle(news.getTitle());
        briefNewsResponse.setDescription(news.getDescription());
        briefNewsResponse.setBody(news.getBody());
        briefNewsResponse.setCreatedAt(news.getCreatedAt());
        briefNewsResponse.setUpdatedAt(news.getUpdatedAt());
        briefNewsResponse.setCommentsCount(news.getComments().size()); // -> добавил

        return briefNewsResponse;
    }


}

