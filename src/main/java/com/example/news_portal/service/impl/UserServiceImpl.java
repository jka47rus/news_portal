package com.example.news_portal.service.impl;

import com.example.news_portal.dto.request.NewsFilter;
import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Role;
import com.example.news_portal.model.User;
import com.example.news_portal.repository.RoleRepository;
import com.example.news_portal.repository.UserRepository;
import com.example.news_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public final RoleRepository roleRepository;


    @Override
    public List<User> findAll(NewsFilter filter) {
        return userRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();

    }

    @Override
    public User save(User user, Role role) {

        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        role.setUser(user);
        roleRepository.save(role);


        return user;
    }

    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User existedUser = findById(user.getId());
        BeanUtils.copyProperties(user, existedUser);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        roleRepository.deleteAll(userRepository.findById(id).get().getRoles());
        userRepository.deleteById(id);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("User with Id {0} not found", id)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormat.format("User with username {0} not found!", username)
                )
        );
    }


}
