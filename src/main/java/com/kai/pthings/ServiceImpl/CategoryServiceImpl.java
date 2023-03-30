package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.ApplicationConfig;
import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Article;
import com.kai.pthings.entity.Category;
import com.kai.pthings.repository.CategoryRepository;
import com.kai.pthings.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@EnableTransactionManagement
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public Category add_new(Category category) {
        category.setId(UUID.randomUUID().toString());
        category.setName(category.getName().strip());
        category.setMeta_name(Snippets.convertMeta(category.getName()));
        return categoryRepository.save(category);
    }

        @Override
        @Transactional
    public Category update_category(Category category) {
        Category data_cate = categoryRepository.findById(category.getId()).get();
        if(!category.getName().equals(data_cate.getName())) {
            data_cate.setName(category.getName().strip());
            data_cate.setMeta_name(Snippets.convertMeta(category.getName().strip()));
        }
        data_cate.setDisplay(category.isDisplay());
        return categoryRepository.save(data_cate);
    }

    @Override
    public void remove_category(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findByMetaName(String meta_name) {
        return categoryRepository.findByMetaName(meta_name);
    }

    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<Category> getAllDisplayCategory() {
        return categoryRepository.getAllDisplay();
    }

    @Override
    public Page<Article> getAllDisplayArticleByMetaName(Pageable pageable, String name) {
        Category category = categoryRepository.findByMetaName(name);
        if (category != null){
            List<Article> articles = category.getArticles();
            return  new PageImpl<>(articles, pageable, articles.size());
        }
      return null;
    }
}
