package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Article;
import com.kai.pthings.repository.ArticleRepository;
import com.kai.pthings.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public Article add_new(Article article){
        article.setCreated_at(Snippets.getCurrentDateTime());
        article.setTitle(article.getTitle().strip());
        article.setLast_edited(Snippets.getCurrentDateTime());
        article.setMeta_name(Snippets.convertMeta(article.getTitle()));
        article.setId(UUID.randomUUID().toString());
        article.setAuthor(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (article.getFeatured_image().isEmpty()) article.setFeatured_image("");
        return articleRepository.save(article);
    }

    @Override
    public Page<Article> getAllDisplayArticle(Pageable pageable) {
        return articleRepository.getAllDisplayArticle(pageable);
    }

    @Override
    public Article update_article(Article article) {
        return null;
    }

    @Override
    public void remove_articleById(String id) {

    }

    @Override
    public Article getArticleByMetaName(String meta_name) {
        return articleRepository.findByMetaName(meta_name);
    }
}
