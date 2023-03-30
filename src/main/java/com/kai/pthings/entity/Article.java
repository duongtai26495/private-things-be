package com.kai.pthings.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @Column(name = "id")
    private String id;

    private String title;

    @Column(length = 2000)
    private String content;

    @Column(updatable = false)
    private String created_at;


    private String last_edited;

    private boolean display;

    private String featured_image;

    private String meta_name;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;


    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;


}
