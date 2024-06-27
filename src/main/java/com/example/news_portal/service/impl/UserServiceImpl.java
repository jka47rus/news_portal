package com.example.news_portal.service.impl;

import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.UserRepository;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());
        BeanUtils.copyProperties(user, existedUser);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("User with Id {0} not found", id)));
    }

    @Override
    public boolean existsByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty()) return false;

        return true;
    }

    @Override
    public boolean existByEmail(String email) {
        if (userRepository.findByEmail(email).isEmpty()) return false;
        return true;
    }


//    @Override
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
//                        .format("User with username {0} not found!", username)));
//    }
//
//    @Override
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
//                        .format("User with email {0} not found!", email)));
//    }


}
