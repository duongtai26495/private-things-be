package com.kai.pthings.controller;

import com.kai.pthings.ServiceImpl.CategoryServiceImpl;
import com.kai.pthings.ServiceImpl.TagServiceImpl;
import com.kai.pthings.ServiceImpl.UserServiceImpl;
import com.kai.pthings.config.ApplicationConfig;
import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.*;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private TagServiceImpl tagService;
    @PostMapping("/category/new")
    public ResponseEntity new_category(@RequestBody Category category){
        if (isAdmin()){
            String meta_name = Snippets.convertMeta(category.getName());
            if(categoryService.findByMetaName(meta_name) == null){
                return ResponseEntity.ok().body(categoryService.add_new(category));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.CATEGORY_EXIST);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @PutMapping("/category/update")
    public ResponseEntity update_category(@RequestBody Category category){
        if (isAdmin()){
            if(categoryService.findById(category.getId()) != null){
                return ResponseEntity.ok().body(categoryService.update_category(category));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.CATEGORY_EXIST);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @DeleteMapping("/category/remove/{id}")
    public ResponseEntity remove_category(@PathVariable String id){
        if (isAdmin()){
            if (categoryService.findById(id) != null){
                categoryService.remove_category(id);
                return ResponseEntity.ok().body(String.format(Snippets.DELETED_SUCCESS,"category"));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.TAG_EXIST);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @PostMapping("/tag/new")
    public ResponseEntity new_tag(@RequestBody Tag tag){
        if (isAdmin()){
            String meta_name = Snippets.convertMeta(tag.getName());
            if(tagService.findByMetaName(meta_name) == null){
                return ResponseEntity.ok().body(tagService.add_new(tag));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.TAG_EXIST);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @PutMapping("/tag/update/")
    public ResponseEntity update_tag(@RequestBody Tag tag){
        if (isAdmin()){
            if (tagService.isExistById(tag.getId())){
                    return ResponseEntity.ok().body(tagService.update_tag(tag));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.TAG_EXIST);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    @DeleteMapping("/tag/remove/{id}")
    public ResponseEntity remove_tag(@PathVariable String id){
        if (isAdmin()){
            if (tagService.isExistById(id)){
                tagService.remove_tag(id);
                return ResponseEntity.ok().body(String.format(Snippets.DELETED_SUCCESS,"tag"));
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Snippets.TAG_EXIST);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Snippets.NOT_PERMISSION);
    }

    private boolean isAdmin () {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        for (Role role : user.getRoles()) {
            if (role.getName().equalsIgnoreCase(Snippets.ROLE_ADMIN)) {
                return true;
            }
        }
        return false;
    }
}
