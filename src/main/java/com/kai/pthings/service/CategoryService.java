package com.kai.pthings.service;

import com.kai.pthings.entity.Article;
import com.kai.pthings.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category add_new(Category category);

    Category update_category(Category category);

    void remove_category(String id);

    Category findByMetaName(String meta_name);

    Category findById(String id);

    List<Category> getAllDisplayCategory();

    Page<Article> getAllDisplayArticleByMetaName(Pageable pageable, String name);
}
