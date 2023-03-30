package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Article;
import com.kai.pthings.entity.Category;
import com.kai.pthings.entity.Tag;
import com.kai.pthings.repository.TagRepository;
import com.kai.pthings.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag add_new(Tag tag) {
        tag.setId(UUID.randomUUID().toString());
        tag.setName(tag.getName().strip());
        tag.setMeta_name(Snippets.convertMeta(tag.getName().strip()));
        return tagRepository.save(tag);
    }

    @Override
    public boolean isExistById(String id){
        return tagRepository.existsById(id);
    }

    @Override
    public Tag update_tag(Tag tag) {
        Tag data_tag = tagRepository.findById(tag.getId()).get();
        if(!tag.getName().equals(data_tag.getName())){
            data_tag.setName(tag.getName().strip());
            data_tag.setMeta_name(Snippets.convertMeta(tag.getName().strip()));
        }
        if (!tag.getColor().equals(data_tag.getColor())) data_tag.setColor(tag.getColor());
        data_tag.setDisplay(tag.isDisplay());
        return tagRepository.save(data_tag);
    }

    @Override
    public Tag findByMetaName(String meta_name) {
        return tagRepository.findByMetaName(meta_name);
    }

    @Override
    public void remove_tag(String id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Page<Article> getAllDisplayArticleWithMetaName(Pageable pageable, String meta_name) {
        Tag tag = tagRepository.findByMetaName(meta_name);
        if (tag != null){
            List<Article> articles = tag.getArticles();
            return  new PageImpl<>(articles, pageable, articles.size());
        }
        return null;
    }

    @Override
    public List<Tag> getAllDisplayTag() {
        return tagRepository.getAllDisplayTags();
    }
}
