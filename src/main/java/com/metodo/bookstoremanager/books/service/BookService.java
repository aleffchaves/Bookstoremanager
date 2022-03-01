package com.metodo.bookstoremanager.books.service;

import com.metodo.bookstoremanager.author.service.AuthorService;
import com.metodo.bookstoremanager.books.mapper.BookMapper;
import com.metodo.bookstoremanager.books.repository.BookRepository;
import com.metodo.bookstoremanager.publishers.service.PublisherService;
import com.metodo.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;



}
