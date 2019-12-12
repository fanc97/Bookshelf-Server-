package com.bookshelf.spring.repository;

import com.bookshelf.spring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    @Query("select u from User u where username like %?1%")
    Page<User> pretraga(
            @Param("username") String username,
            Pageable pageRequest);
}
