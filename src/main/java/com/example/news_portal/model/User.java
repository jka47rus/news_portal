package com.example.news_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    @OneToMany
    private List<News> news = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public void addComment(Comment comment) {
        comment.setUser(this);
        comments.add(comment);
    }

    public void addNews(News oneNews) {
        oneNews.setAuthor(this);
        news.add(oneNews);
    }

    public void deleteNews(News one) {
        news.remove(one);
    }

    public void deleteComment(Comment one) {
        comments.remove(one);
    }


}
