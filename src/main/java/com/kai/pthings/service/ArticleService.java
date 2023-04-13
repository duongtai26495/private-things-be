package com.kai.pthings.service;

import com.kai.pthings.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;

public interface ArticleService {

    Article add_new(Article article) throws ParseException;
    Page<Article> getAllDisplayArticle(Pageable pageable);
    Article update_article(Article article);
    Article findById(String id);
    Article getArticleByMetaName(String meta_name);
    void remove_articleById(String id);
    boolean isExistById(String id);
}
