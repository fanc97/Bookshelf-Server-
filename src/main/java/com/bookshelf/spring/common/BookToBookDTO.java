package com.bookshelf.spring.common;


import com.bookshelf.spring.dto.BookDTO;
import com.bookshelf.spring.entity.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookToBookDTO implements Converter<Book, BookDTO> {
    @Override
    public BookDTO convert(Book source) {
        BookDTO dto= new BookDTO();
        dto.setId(source.getId());
        dto.setAuthor(source.getAuthor());
        dto.setTitle(source.getTitle());
        dto.setInStok(source.getInStock());
        return dto;
    }
    public List<BookDTO> convert(List<Book> source){
        List<BookDTO> dto= new ArrayList<>();
        for(Book b : source){
            dto.add(convert(b));
        }
        return dto;
    }


}
