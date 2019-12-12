package com.bookshelf.spring.controller;


import com.bookshelf.spring.common.BookDTOToBook;
import com.bookshelf.spring.common.BookToBookDTO;
import com.bookshelf.spring.dto.BookDTO;
import com.bookshelf.spring.entity.Book;
import com.bookshelf.spring.entity.User;
import com.bookshelf.spring.service.BookService;
import com.bookshelf.spring.service.Impl.NotificationServiceImpl;
import com.bookshelf.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/books")
public class ApiBookController {
    @Autowired
    BookService bookService;
    @Autowired
    BookToBookDTO toDTO;
    @Autowired
    BookDTOToBook toBook;
    @Autowired
    UserService userService;
    @Autowired
    NotificationServiceImpl notificationService;
//    1
//    @RequestMapping(method = RequestMethod.GET)
//    public Page<Book>getAll(@RequestParam(defaultValue = "0") int page){
//        return bookService.findAll(page);
//    }

//    2
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<BookDTO>>getAll(@RequestParam(defaultValue = "0") int page){
//        Page<Book> books= bookService.findAll(page);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("totalPages", Integer.toString(books.getTotalPages()));
//
//        return new ResponseEntity<>(
//                toDTO.convert(books.getContent()),
//                headers,
//                HttpStatus.OK);
//    }


//    3
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BookDTO>> getBooksPage(@RequestParam(defaultValue = "0") int page) {
        Page<Book> book = bookService.findAll(page);
        List<BookDTO> retVal = new ArrayList<>();
        for (Book b : book) {
            BookDTO dto=new BookDTO(b);
            dto.setTotalPages(book.getTotalPages());
            retVal.add(dto);
        }
        return new ResponseEntity<List<BookDTO>>(retVal, HttpStatus.OK);
    }


    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseEntity<List<BookDTO>> getPretraga(
            @RequestParam Optional<String> title
            , @RequestParam Optional<String> author
            , @RequestParam(defaultValue = "0") int page) {
        Page<Book> book = bookService.pretraga(title.orElse("_"),author.orElse("_"),page);
        List<BookDTO> retVal = new ArrayList<>();
        for (Book b : book) {
            BookDTO dto=new BookDTO(b);
            dto.setTotalPages(book.getTotalPages());
            retVal.add(dto);
        }
        return new ResponseEntity<List<BookDTO>>(retVal, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BookDTO>save(@RequestBody  BookDTO bookDto){
        Book book = toBook.convert(bookDto);
        bookService.save(book);
        return new ResponseEntity<>(toDTO.convert(book),HttpStatus.CREATED);

    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<BookDTO>update(@PathVariable Long id,@RequestBody BookDTO book){
        if(!id.equals(book.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book b = toBook.convert(book);
        bookService.save(b);
        return new ResponseEntity<>(toDTO.convert(b),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<BookDTO>delete(@PathVariable Long id){

        Book book = bookService.getOne(id);
        if(book==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bookService.delete(id);
        return  new ResponseEntity<>(toDTO.convert(book),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @RequestMapping(value = "/order",method = RequestMethod.GET)
    public ResponseEntity<BookDTO>order(@RequestParam Long bookId,
                                        @RequestParam Integer userId){
        User user = userService.findOne(userId);
        Book book = bookService.getOne(bookId);
        notificationService.orderBook(user,book);
        book.setInStock(book.getInStock()-1);
        bookService.save(book);

        if(book==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<>(toDTO.convert(book),HttpStatus.OK);
    }

}
