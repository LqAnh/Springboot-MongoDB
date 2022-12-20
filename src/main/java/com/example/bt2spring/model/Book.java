package com.example.bt2spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "anhlq36")
public class Book {
    @Id
    private String id;
    private @TextIndexed String bookName;
    private @TextIndexed String authorName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;
    private String description;
}
