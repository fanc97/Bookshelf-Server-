package com.bookshelf.spring.repository;


import com.bookshelf.spring.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {



    @Query("select b from Book b where title like %?1% and author like %?2%")
    Page<Book> pretraga(
            @Param("title") String title,
            @Param("author") String author,
            Pageable pageRequest);
}
