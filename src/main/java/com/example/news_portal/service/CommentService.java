package com.example.news_portal.service;

import com.example.news_portal.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<Comment> findAll();

    Comment addComment(Comment comment, UUID userId, UUID newsId);

    Comment updateComment(Comment comment);

    void deleteById(UUID id);

    Comment findById(UUID id);

}
