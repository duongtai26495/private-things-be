package com.kai.pthings.controller;

import com.kai.pthings.ServiceImpl.ArticleServiceImpl;
import com.kai.pthings.ServiceImpl.CategoryServiceImpl;
import com.kai.pthings.ServiceImpl.UserServiceImpl;
import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Article;
import com.kai.pthings.entity.Role;
import com.kai.pthings.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ArticleServiceImpl articleService;

    @PostMapping("/new_article")
    public ResponseEntity new_article(@RequestBody Article article) throws ParseException {
        if(isUser()){
            return ResponseEntity.ok().body(articleService.add_new(article));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);

    }

    private boolean isUser () {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        for (Role role : user.getRoles()) {
            if (role.getName().equalsIgnoreCase(Snippets.ROLE_USER)) {
                return true;
            }
        }
        return false;
    }
}
