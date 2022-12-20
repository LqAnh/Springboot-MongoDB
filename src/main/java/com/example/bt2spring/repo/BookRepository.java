package com.example.bt2spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findById(String id);

    Page<Book> findByDateCreatedBetweenOrderByDateCreatedDesc(Date start, Date end, Pageable page);

    Page<Book> findByDateCreatedBetweenOrderByDateCreatedAsc(Date start, Date end, Pageable page);

    Page<Book> findBooksByBookNameContainsAndAuthorNameContains(String bookName, String authorName, Pageable page);

    @Query("{$text: {$search: ?0, $caseSensitive: true}}")
    List<Book> findByQuery(String search);
}
