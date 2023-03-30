package com.kai.pthings.repository;

import com.kai.pthings.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.uname = :uname")
    Optional<User> findByUname(@Param("uname") String uname);

    @Query("SELECT u FROM User u WHERE u.uname = :uname")
    User findUserByUname(@Param("uname") String uname);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
