package com.kai.pthings.controller;

import com.kai.pthings.ServiceImpl.ArticleServiceImpl;
import com.kai.pthings.ServiceImpl.CategoryServiceImpl;
import com.kai.pthings.ServiceImpl.StorageServiceImpl;
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
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {


    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private StorageServiceImpl storageService;


    @PostMapping("/new")
    public ResponseEntity new_article(@RequestBody Article article) throws ParseException {
        if(isUser()){
            return ResponseEntity.ok().body(articleService.add_new(article));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }


    @PutMapping("/update")
    public ResponseEntity update_article(@RequestBody Article article){
        if (isUser()){
            Article data_article = articleService.findById(article.getId());
            if(data_article != null){
                return ResponseEntity.ok().body(articleService.update_article(article));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.DIARY_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity remove_article(@PathVariable String id){
        Article data_article = articleService.findById(id);
        if(data_article != null){
            if(data_article.getAuthor().getUname().equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()))
            {
                articleService.remove_articleById(id);
                if (articleService.isExistById(id)){
                    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.FAILED);
                }
                return ResponseEntity.status(HttpStatus.OK).body(String.format(Snippets.DELETED_SUCCESS,"Article"));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.DIARY_NOT_FOUND);
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
