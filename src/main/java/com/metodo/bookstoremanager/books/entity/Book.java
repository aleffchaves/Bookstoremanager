package com.metodo.bookstoremanager.books.entity;

import com.metodo.bookstoremanager.author.entity.Author;
import com.metodo.bookstoremanager.entity.Auditable;
import com.metodo.bookstoremanager.publishers.entity.Publisher;
import com.metodo.bookstoremanager.users.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false)
    private String isbn;

    @Column(columnDefinition = "integer default 0")
    private int pages;

    @Column(columnDefinition = "integer default 0")
    private int chapters;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Author author;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Publisher publisher;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
}
