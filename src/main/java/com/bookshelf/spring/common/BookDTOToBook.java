package com.bookshelf.spring.common;


import com.bookshelf.spring.dto.BookDTO;
import com.bookshelf.spring.entity.Book;
import com.bookshelf.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookDTOToBook implements Converter<BookDTO, Book> {
    @Autowired
    BookService bookService;
    @Override
    public Book convert(BookDTO source) {
        Book book;
        if(source.getId()==null){
             book = new Book();
        }else{
             book =bookService.getOne(source.getId());
        }
        book.setId(source.getId());
        book.setAuthor(source.getAuthor());
        book.setTitle(source.getTitle());
        book.setInStock(source.getInStok());
        return book;
    }
}
