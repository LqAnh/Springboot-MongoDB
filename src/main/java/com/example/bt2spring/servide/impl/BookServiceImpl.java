package com.example.bt2spring.servide.impl;

import com.example.bt2spring.repo.BookRepository;
import com.example.bt2spring.servide.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.bt2spring.exception.ApiRequestException;

import java.awt.print.Book;
import java.util.Date;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Page<Book> getAllBook(int page) {
        if (page<0) {
            throw new API("Page or size is not valid");
        } else {
            Pageable sortedByDate = PageRequest.of(page, 10, Sort.by("dateCreated").descending());
            return bookRepository.findAll(sortedByDate);
        }
    }

    @Override
    public Book createBook(Book book) {
        if (!checkInput(book.getBookName(), book.getAuthorName(), book.getDateCreated(), book.getDescription())) {
            throw new ApiRequestException("Field can not be empty or null");
        } else {
            bookRepository.save(book);
        }
        return book;
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Book Not Found"));
    }

    @Override
    public Page<Book> findBookByNameAndAuthor(String bookName, String authorName, int page) {
        Pageable sortedByDate = PageRequest.of(page, 10);
        return bookRepository.findBooksByBookNameContainsAndAuthorNameContains(bookName, authorName, sortedByDate);
    }


    @Override
    public Page<Book> findBookByDateV1(Date start, Date end, int page) {
        Pageable sortedByDate = PageRequest.of(page, 10);
        if (start.before(end)){
            return bookRepository.findByDateCreatedBetweenOrderByDateCreatedAsc(start, end, sortedByDate);
        }else{
            return bookRepository.findByDateCreatedBetweenOrderByDateCreatedDesc(end, start, sortedByDate);
        }
    }


    @Override
    public String delBook(String id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
            return "Deleted Successfully " + id;
        } else {
            throw new ApiRequestException("Book Not Exist: " + id);
        }
    }

    @Override
    public Book updateBook(String id, Book newBook) {
        Book updateBook = bookRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Book Not Exist: " + id));
        if (!checkInput(newBook.getBookName(), newBook.getAuthorName(), newBook.getDateCreated(), newBook.getDescription())) {
            throw new ApiRequestException("Field can not be empty or null");
        } else {
            updateBook.setBookName(newBook.getBookName());
            updateBook.setAuthorName(newBook.getAuthorName());
            updateBook.setDateCreated(newBook.getDateCreated());
            updateBook.setDescription(newBook.getDescription());
            bookRepository.save(updateBook);
        }
        return updateBook;
    }


    private boolean checkInput(String bookName, String authorName, Date create, String des) {
        if (bookName == null || authorName == null || create == null || des == null) {
            return false;
        } else if (Objects.equals(bookName, "") || Objects.equals(authorName, "") || Objects.equals(create, "") || Objects.equals(des, "")) {
            return false;
        } else {
            return true;
        }
    }

}
