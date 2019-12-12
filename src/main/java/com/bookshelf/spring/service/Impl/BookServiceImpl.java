package com.bookshelf.spring.service.Impl;

import com.bookshelf.spring.entity.Book;
import com.bookshelf.spring.repository.BookRepository;
import com.bookshelf.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public Page<Book> findAll(int pageNum) {
        return bookRepository.findAll(
                new PageRequest(pageNum, 5));
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

//    @Override
//    public Page<Book> pretraga(Optional<String> title, Optional<String> author, int page) {
//        return bookRepository.pretraga(title.orElse("_"),author.orElse("_"),new PageRequest(page,5));
//    }

    @Override
    public Page<Book> pretraga(String title, String author, int page) {
        return bookRepository.pretraga(title,author,new PageRequest(page,5));
    }


}
