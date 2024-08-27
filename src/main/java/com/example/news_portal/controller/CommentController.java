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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentMap commentMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentListResponse> findAll() {
        return ResponseEntity.ok(commentMapper.commentListToCommentListResponse(commentService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> getBiId(@PathVariable UUID id) {
        return ResponseEntity.ok(commentMapper.commentToResponse(commentService.findById(id)));
    }

    @PostMapping("/{newsId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request,
                                                      @PathVariable UUID newsId,
                                                      @AuthenticationPrincipal UserDetails userDetails) {

        Comment comment = commentService.addComment(commentMapper.fromRequestToComment(request), userDetails.getUsername(), newsId);
        return ResponseEntity.ok(commentMapper.commentToResponse(comment));

    }

    @PutMapping("/{id}")
    @Loggable
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable UUID id,
                                                         @AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody CommentRequest request) {
        Comment comment = commentService.updateComment(commentMapper.requestToComment(id, request));

        return ResponseEntity.ok(commentMapper.commentToResponse(comment));
    }

    @DeleteMapping("/{id}")
    @Loggable
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
