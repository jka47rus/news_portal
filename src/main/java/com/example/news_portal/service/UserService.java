package com.example.news_portal.service;

import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.model.Role;
import com.example.news_portal.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAll(NewsFilter filter);

    User save(User user, Role role);

    User update(User user);

    void deleteById(UUID id);

    User findById(UUID id);

    boolean existsByUsername(String username);

    boolean existByEmail(String email);

    User findByUsername(String username);

}
