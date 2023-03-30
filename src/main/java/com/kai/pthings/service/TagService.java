package com.kai.pthings.service;

import com.kai.pthings.entity.Article;
import com.kai.pthings.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {


    Tag add_new(Tag tag);

    Tag update_tag(Tag tag);

    Tag findByMetaName(String meta_name);

    boolean isExistById (String id);

    void remove_tag(String id);

    Page<Article> getAllDisplayArticleWithMetaName(Pageable pageable, String meta_name);

    List<Tag> getAllDisplayTag();
}
