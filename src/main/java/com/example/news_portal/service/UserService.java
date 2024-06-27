package com.example.news_portal.service;

import com.example.news_portal.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    User save(User user);

    User update(User user);

    void deleteById(UUID id);

    User findById(UUID id);

    boolean existsByUsername(String username);

    boolean existByEmail(String email);


//    User findByUsername(String username);
//    User findByEmail(String email);


}
