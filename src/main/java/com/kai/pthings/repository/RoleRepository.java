package com.kai.pthings.repository;

import com.kai.pthings.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(@Param("name") String name);
}
