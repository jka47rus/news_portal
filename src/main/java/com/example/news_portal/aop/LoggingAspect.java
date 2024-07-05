package com.example.news_portal.aop;

import com.example.news_portal.exception.EntityNotFoundException;
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
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;

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
            commentCheck(args);
        }
        if (joinPoint.getSignature().getName().contains("News")) {
            newsCheck(args);
        }

    }

    private void commentCheck(Object[] args) {
        Comment comment = commentService.findById((UUID) args[1]);
        User user = userService.findById((UUID) args[0]);
        System.out.println(comment.getComment());
        System.out.println(user.getUsername());

        if (!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new EntityNotFoundException(MessageFormat
                    .format("User with Id {0} not created this comment", (UUID) args[0]));
        }
    }

    private void newsCheck(Object[] args) {

        News news = newsService.findById((UUID) args[1]);
        User user = userService.findById((UUID) args[0]);

        if (!user.getUsername().equals(news.getAuthor().getUsername())) {
            throw new EntityNotFoundException(MessageFormat
                    .format("User with Id {0} not created this news", (UUID) args[0]));
        }
    }


}
