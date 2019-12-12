package com.bookshelf.spring.dto;

import com.bookshelf.spring.entity.Book;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private int inStok;
    private int totalPages;
    public BookDTO() {
    }
    public BookDTO(Book book){
        this.id = book.getId();
        this.title=book.getTitle();
        this.author=book.getAuthor();
        this.inStok=book.getInStock();
    }

    public int getInStok() {
        return inStok;
    }

    public void setInStok(int inStok) {
        this.inStok = inStok;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
