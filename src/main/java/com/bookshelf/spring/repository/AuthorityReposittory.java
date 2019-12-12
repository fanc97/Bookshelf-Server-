package com.bookshelf.spring.repository;

import com.bookshelf.spring.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityReposittory extends JpaRepository<Authority,Integer> {

    Authority findByName(String name);
}
