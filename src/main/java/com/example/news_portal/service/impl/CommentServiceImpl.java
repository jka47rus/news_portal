package com.example.news_portal.service.impl;

import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.CommentRepository;
import com.example.news_portal.service.CommentService;
import com.example.news_portal.service.NewsService;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }

    @Override
    public Comment addComment(Comment comment, UUID userId, UUID newsId) {
        User author = userService.findById(userId);
        News news = newsService.findById(newsId);
        author.addComment(comment);
        news.addComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        BeanUtils.copyProperties(comment, updatedComment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(UUID id) {
        Comment comment = commentRepository.findById(id).get();
        User author = userService.findById(comment.getUser().getId());

        author.deleteComment(comment);

        commentRepository.deleteById(id);
    }

    @Override
    public Comment findById(UUID id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Comment with Id {0} not found", id)));
    }

}
