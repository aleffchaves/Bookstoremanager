package com.metodo.bookstoremanager.books.service;

import com.metodo.bookstoremanager.author.service.AuthorService;
import com.metodo.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.metodo.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.metodo.bookstoremanager.books.mapper.BookMapper;
import com.metodo.bookstoremanager.books.repository.BookRepository;
import com.metodo.bookstoremanager.publishers.service.PublisherService;
import com.metodo.bookstoremanager.users.dto.AuthenticatedUser;
import com.metodo.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("alef", "123456", "ADMIN");
    }
}
