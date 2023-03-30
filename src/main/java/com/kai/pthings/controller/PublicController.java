package com.kai.pthings.controller;


import com.kai.pthings.ServiceImpl.ArticleServiceImpl;
import com.kai.pthings.ServiceImpl.CategoryServiceImpl;
import com.kai.pthings.ServiceImpl.RoleServiceImpl;
import com.kai.pthings.ServiceImpl.TagServiceImpl;
import com.kai.pthings.config.AuthenticationService;
import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Article;
import com.kai.pthings.entity.AuthenticateRequest;
import com.kai.pthings.entity.AuthenticationResponse;
import com.kai.pthings.entity.RegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final AuthenticationService service;

    private final CategoryServiceImpl categoryService;

    private final TagServiceImpl tagService;

    private final ArticleServiceImpl articleService;
    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/category={name}")
    public Page<Article> getCategoryByMetaName(@RequestParam(defaultValue = Snippets.LAST_EDITED_DESC) String sort,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size,
                                               @PathVariable("name") String name){

        Sort.by(Snippets.LAST_EDITED_DESC).descending();
        Sort sorted = switch (sort) {
            case Snippets.CREATED_AT_ASC -> Sort.by(Snippets.CREATED_AT).ascending();
            case Snippets.CREATED_AT_DESC -> Sort.by(Snippets.CREATED_AT).descending();
            case Snippets.A_Z -> Sort.by(Snippets.TITLE).ascending();
            case Snippets.Z_A -> Sort.by(Snippets.TITLE).descending();
            default -> Sort.by(Snippets.LAST_EDITED_DESC).descending();
        };
        Pageable pageable = PageRequest.of(page,size,sorted);

        return categoryService.getAllDisplayArticleByMetaName(pageable, name);
    }

    @GetMapping("/tag={name}")
    public Page<Article> getArticleByMetaNameTag(@RequestParam(defaultValue = Snippets.LAST_EDITED_DESC) String sort,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size,
                                               @PathVariable("name") String name){

        Sort.by(Snippets.LAST_EDITED_DESC).descending();
        Sort sorted = switch (sort) {
            case Snippets.CREATED_AT_ASC -> Sort.by(Snippets.CREATED_AT).ascending();
            case Snippets.CREATED_AT_DESC -> Sort.by(Snippets.CREATED_AT).descending();
            case Snippets.A_Z -> Sort.by(Snippets.TITLE).ascending();
            case Snippets.Z_A -> Sort.by(Snippets.TITLE).descending();
            default -> Sort.by(Snippets.LAST_EDITED_DESC).descending();
        };
        Pageable pageable = PageRequest.of(page,size,sorted);

        return tagService.getAllDisplayArticleWithMetaName(pageable, name);
    }

    @GetMapping("/articles")
    public Page<Article> getAllDisplayArticles(@RequestParam(defaultValue = Snippets.LAST_EDITED_DESC) String sort,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size){

        Sort.by(Snippets.LAST_EDITED_DESC).descending();
        Sort sorted = switch (sort) {
            case Snippets.CREATED_AT_ASC -> Sort.by(Snippets.CREATED_AT).ascending();
            case Snippets.CREATED_AT_DESC -> Sort.by(Snippets.CREATED_AT).descending();
            case Snippets.A_Z -> Sort.by(Snippets.TITLE).ascending();
            case Snippets.Z_A -> Sort.by(Snippets.TITLE).descending();
            default -> Sort.by(Snippets.LAST_EDITED_DESC).descending();
        };
        Pageable pageable = PageRequest.of(page,size,sorted);

        return articleService.getAllDisplayArticle(pageable);
    }

    @GetMapping("/article={name}")
    public ResponseEntity getArticleByMetaName(@PathVariable("name") String name){
        return ResponseEntity.ok().body(articleService.getArticleByMetaName(name));
    }

    @GetMapping("tags")
    public ResponseEntity getAllTag(){
        return ResponseEntity.ok().body(tagService.getAllDisplayTag());
    }

    @GetMapping("categories")
    public ResponseEntity getAllCategories(){
        return ResponseEntity.ok().body(categoryService.getAllDisplayCategory());
    }


    @GetMapping("/generate_roles")
    public String generateRoles(){
        roleService.generateRole();
        return "Done !";
    }
}
