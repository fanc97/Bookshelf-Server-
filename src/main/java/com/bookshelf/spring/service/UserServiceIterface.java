package com.bookshelf.spring.service;

import com.bookshelf.spring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserServiceIterface {

    User findOne(Integer userId);

    Page<User> findAll(int pageNum);

    User findByUsername(String username);

    List<User> findAll();

    User save(User users);

    void remove(Integer id);


	User getUserByName(String name);

    Page<User> pretraga(
            @Param("username") String username,
            int page);
}
