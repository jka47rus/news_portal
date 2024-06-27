package com.example.news_portal.repository;

import com.example.news_portal.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
//    Page<Comment> findAllByPostId(UUID postId, Pageable pageable);
//    boolean existByIdUserId(UUID id, UUID userId);
}
