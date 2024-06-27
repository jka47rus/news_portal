package com.example.news_portal.mapper;

import com.example.news_portal.dto.request.CommentRequest;
import com.example.news_portal.dto.response.CommentListResponse;
import com.example.news_portal.dto.response.CommentResponse;
import com.example.news_portal.model.Comment;
import com.example.news_portal.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentMap {

    private final CommentService commentService;


    public List<CommentResponse> commentListToListResponse(List<Comment> comments) {
        return comments.stream().map(this::commentToResponse).collect(Collectors.toList());
    }

    public CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setCommentList(commentListToListResponse(comments));
        return response;
    }

    public CommentResponse commentToResponse(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setUsername(comment.getUser().getUsername());
        commentResponse.setId(comment.getId());
        commentResponse.setComment(comment.getComment());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getUpdatedAt());

        return commentResponse;
    }


    public Comment fromRequestToComment(CommentRequest request) {
        if (request == null) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId(request.getId());
        comment.setComment(request.getComment());

        return comment;
    }

    public Comment requestToComment(UUID id, CommentRequest request) {
        if (id == null && request == null) {
            return null;
        }

        Comment comment = commentService.findById(id);
        comment.setComment(request.getComment());
        return comment;
    }

}
