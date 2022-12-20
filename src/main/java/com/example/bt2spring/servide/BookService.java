package com.example.bt2spring.servide;

import org.springframework.data.domain.Page;

import java.awt.print.Book;
import java.util.Date;

public interface BookService {

    Page<Book> getAllBook(int page);

    Book createBook(Book book);

    Book findById(String id);

    Page<Book> findBookByNameAndAuthor(String bookName, String authorName, int page);

    String delBook(String id);

    Book updateBook(String id, Book book);

    Page<Book> findBookByDateV1(Date start, Date end, int page);

}
