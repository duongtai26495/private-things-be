package com.kai.pthings.repository;

import com.kai.pthings.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    @Query("SELECT t FROM Tag t WHERE t.meta_name = :meta_name AND t.display = 1")
    Tag findByMetaName(@Param("meta_name") String name);

    @Query("SELECT t FROM Tag t WHERE t.display = 1")
    List<Tag> getAllDisplayTags();
}
