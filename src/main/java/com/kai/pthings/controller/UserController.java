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
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Objects;

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

    @Autowired
    private StorageServiceImpl storageService;

    @PostMapping("/new_article")
    public ResponseEntity new_article(@RequestBody Article article) throws ParseException {
        if(isUser()){
            return ResponseEntity.ok().body(articleService.add_new(article));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @PostMapping("upload_image")
    public ResponseEntity uploadImageWithUsername(@RequestParam("image") MultipartFile file, @RequestParam(defaultValue = "") String username){
        String filename = "";
        if(!username.equals("")){
            if(username.equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())){
                filename = storageService.storeFile(file, username);
                if(filename != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(
                            filename
                    );
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    filename
            );

        }else{
            filename = storageService.storeFile(file, "noname");
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    Objects.requireNonNullElse(filename, Snippets.STORE_FILE_FAILED)
            );
        }

    }

    @PutMapping("/update_article")
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
