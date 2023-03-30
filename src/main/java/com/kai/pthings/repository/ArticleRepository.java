package com.kai.pthings.repository;

import com.kai.pthings.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    @Query("SELECT a FROM Article a WHERE a.meta_name = :meta_name AND a.display = 1")
    Article findByMetaName(@Param("meta_name") String name);

    @Query("SELECT a FROM Article a WHERE a.display = 1")
    Page<Article> getAllDisplayArticle(Pageable pageable);


}
