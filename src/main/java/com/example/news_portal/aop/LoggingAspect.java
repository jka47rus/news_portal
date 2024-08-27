package com.example.news_portal.aop;

import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.exception.NotRightsException;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import com.example.news_portal.service.CommentService;
import com.example.news_portal.service.NewsService;
import com.example.news_portal.service.UserService;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Collectors;

@Aspect
@Component
@Data
public class LoggingAspect {

    private final CommentService commentService;
    private final NewsService newsService;
    private final UserService userService;

    @Before("@annotation(Loggable)")
    public void checkUserId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (joinPoint.getSignature().getName().contains("Comment")) {
            if (joinPoint.getSignature().getName().contains("delete")) {
                deleteComment(args);
            } else {
                commentCheck(args);
            }
        }
        if (joinPoint.getSignature().getName().contains("News")) {
            if (joinPoint.getSignature().getName().contains("delete")) {
                deleteNews(args);
            } else {
                newsCheck(args);
            }

        }
        if (joinPoint.getSignature().getName().contains("User")) {
            userCheck(args);
        }

    }

    private void commentCheck(Object[] args) {
        Comment comment = commentService.findById((UUID) args[0]);
        String userName = ((UserDetails) args[1]).getUsername();
        User user = userService.findByUsername(userName);

        if (!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new EntityNotFoundException(MessageFormat
                    .format("User with Id {0} not created this comment", (UUID) args[0]));
        }
    }

    private void newsCheck(Object[] args) {

        News news = newsService.findById((UUID) args[0]);
        String userName = ((UserDetails) args[1]).getUsername();
        User user = userService.findByUsername(userName);

        if (!user.getUsername().equals(news.getAuthor().getUsername())) {
            throw new NotRightsException(MessageFormat
                    .format("User with Id {0} not created this news", (UUID) args[0]));
        }
    }

    private void userCheck(Object[] args) {

        User foundUser = userService.findById((UUID) args[0]);
        String roleOfAsker = getRoles((UserDetails) args[1]);
        if (roleOfAsker.contains("ROLE_USER")) {
            boolean existsRole = foundUser.getRoles().stream().anyMatch(role ->
                    String.valueOf(role.getRoleType()).equals(roleOfAsker));
            if (!existsRole)
                throw new NotRightsException("You don't have enough rights, because you ROLE is: ROLE_USER");
        }
    }

    private void deleteNews(Object[] args) {
        String roleOfAsker = getRoles((UserDetails) args[1]);
        if (roleOfAsker.contains("ROLE_USER")) {
            newsCheck(args);
        }
    }

    private void deleteComment(Object[] args) {
        String roleOfAsker = getRoles((UserDetails) args[1]);
        if (roleOfAsker.contains("ROLE_USER")) {
            commentCheck(args);
        }
    }

    private String getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

    }


}
