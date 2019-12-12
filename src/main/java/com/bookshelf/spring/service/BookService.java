package com.bookshelf.spring.service;


import com.bookshelf.spring.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookService {
    Page<Book> findAll(int pageNum);
    void save(Book book);
    Book getOne(Long id);
    List<Book> getAll();
    void delete(Long id);
    Page<Book> pretraga(
            @Param("title") String title,
            @Param("author") String author,
            int page);
}
