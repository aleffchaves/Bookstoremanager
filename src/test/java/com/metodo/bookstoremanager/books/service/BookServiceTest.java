package com.metodo.bookstoremanager.books.service;

import com.metodo.bookstoremanager.author.entity.Author;
import com.metodo.bookstoremanager.author.service.AuthorService;
import com.metodo.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.metodo.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.metodo.bookstoremanager.books.dto.BookRequestDTO;
import com.metodo.bookstoremanager.books.dto.BookResponseDTO;
import com.metodo.bookstoremanager.books.entity.Book;
import com.metodo.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.metodo.bookstoremanager.books.exception.BookNotFoundException;
import com.metodo.bookstoremanager.books.mapper.BookMapper;
import com.metodo.bookstoremanager.books.repository.BookRepository;
import com.metodo.bookstoremanager.publishers.entity.Publisher;
import com.metodo.bookstoremanager.publishers.service.PublisherService;
import com.metodo.bookstoremanager.users.dto.AuthenticatedUser;
import com.metodo.bookstoremanager.users.entity.User;
import com.metodo.bookstoremanager.users.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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

    @Test
    void whenNewBookIsInformedThenItShouldBeCreated() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();
        Book expectedCreatedBook = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.empty());
        when(authorService.verifyAndGetIfExists(expectedBookToCreateDTO.getAuthorId())).thenReturn(new Author());
        when(publisherService.verifyAndGetIfExists(expectedBookToCreateDTO.getPublisherId())).thenReturn(new Publisher());
        when(bookRepository.save(any(Book.class))).thenReturn(expectedCreatedBook);

        BookResponseDTO createdBookResponseDTO = bookService.create(authenticatedUser, expectedBookToCreateDTO);

      assertThat(createdBookResponseDTO, Matchers.is(equalTo(expectedCreatedBookDTO)));
    }

    @Test
    void whenExistingBookIsInformedToCreateThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();
        Book expectedDuplicatedBook = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.of(expectedDuplicatedBook));
        assertThrows(BookAlreadyExistsException.class, () -> bookService.create(authenticatedUser, expectedBookToCreateDTO));
    }

    @Test
    void whenExistingBookIsInformedThenABookShouldBeReturned() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.of(expectedFoundBook));

        BookResponseDTO foundBookDTO = bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId());

        assertThat(foundBookDTO, is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenNotExistingBookIsInformedThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername()))
                .thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId()));

    }

    @Test
    void whenListBookIsCalledWhenItShouldBeReturned() {
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.singletonList(expectedFoundBook));

        List<BookResponseDTO> returnedBooksResponseList = bookService.findAllByUser(authenticatedUser);

        assertThat(returnedBooksResponseList.size(), is(1));
        assertThat(returnedBooksResponseList.get(0), is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenListBookIsCalledThenAnEmptyListShouldBeReturned() {
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.EMPTY_LIST);

        List<BookResponseDTO> returnedBooksResponseList = bookService.findAllByUser(authenticatedUser);

        assertThat(returnedBooksResponseList.size(), is(0));
    }
}
