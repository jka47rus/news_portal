package com.example.news_portal.controller;

import com.example.news_portal.aop.Loggable;
import com.example.news_portal.dto.request.CommentRequest;
import com.example.news_portal.dto.response.CommentListResponse;
import com.example.news_portal.dto.response.CommentResponse;
import com.example.news_portal.mapper.CommentMap;
import com.example.news_portal.model.Comment;
import com.example.news_portal.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentMap commentMapper;

    @GetMapping
    public ResponseEntity<CommentListResponse> findAll() {
        return ResponseEntity.ok(commentMapper.commentListToCommentListResponse(commentService.findAll()));
    }

    @PostMapping("/{newsId}/{userId}")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request,
                                                      @PathVariable UUID newsId,
                                                      @PathVariable UUID userId) {
        Comment comment = commentService.addComment(commentMapper.fromRequestToComment(request), userId, newsId);
        return ResponseEntity.ok(commentMapper.commentToResponse(comment));

    }

    @PutMapping("/{id}")
    @Loggable
    public ResponseEntity<CommentResponse> updateComment(@RequestParam UUID userId,
                                                         @PathVariable UUID id,
                                                         @RequestBody CommentRequest request
    ) {
        Comment comment = commentService.updateComment(commentMapper.requestToComment(id, request));

        return ResponseEntity.ok(commentMapper.commentToResponse(comment));
    }

    @DeleteMapping("/{id}")
    @Loggable
    public ResponseEntity<Void> deleteComment(@RequestParam UUID userId, @PathVariable UUID id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getBiId(@PathVariable UUID id) {
        return ResponseEntity.ok(commentMapper.commentToResponse(commentService.findById(id)));
    }

}
