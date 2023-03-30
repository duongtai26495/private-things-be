package com.kai.pthings.repository;

import com.kai.pthings.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE c.meta_name = :meta_name AND c.display = 1")
    Category findByMetaName(@Param("meta_name") String name);

    @Query("SELECT c FROM Category c WHERE c.display = 1")
    List<Category> getAllDisplay();
}
