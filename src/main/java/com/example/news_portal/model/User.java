package com.example.news_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
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
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
    @OneToMany
    @ToString.Exclude
    private List<News> news = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

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
