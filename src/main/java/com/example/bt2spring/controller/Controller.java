package com.example.bt2spring.controller;

import com.example.bt2spring.repo.BookRepository;
import com.example.bt2spring.servide.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository repository;


    @GetMapping("/")
    public Page<Book> getAllBook(@RequestParam(value = "page") int page) {
        return bookService.getAllBook(page);
    }

    @GetMapping("/get/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.findById(id);
    }

    @PostMapping("/cr")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/get")
    public Page<Book> getBookByNameAndAuthor(@RequestParam(value = "bookname") String bookName,
                                             @RequestParam(value = "authorname") String authorName,
                                             @RequestParam(value = "page") int page) {
        return bookService.findBookByNameAndAuthor(bookName, authorName, page);
    }

    @DeleteMapping("/del")
    public String delEm(@RequestParam(value = "id") String id) {
        return bookService.delBook(id);
    }

    @PutMapping("/up")
    public Book upEm(@RequestParam(value = "id") String id, @RequestBody Book newDetail) {
        return bookService.updateBook(id, newDetail);
    }

    @GetMapping("/getindex")
    public List<Book> getBookByIndex(@RequestParam(value = "search") String search) {
        return repository.findByQuery(search);
    }

    @GetMapping("/date")
    public Page<Book> getBookBetweenDateV1(
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "start") Date start,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "end") Date end,
            @RequestParam(value = "page") int page) {
        return bookService.findBookByDateV1(start, end, page);
    }
}
