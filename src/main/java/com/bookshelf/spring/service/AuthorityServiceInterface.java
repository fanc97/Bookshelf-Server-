package com.bookshelf.spring.service;

import com.bookshelf.spring.entity.Authority;

public interface AuthorityServiceInterface  {
    Authority findByName(String name);
}
