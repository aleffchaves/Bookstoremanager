package com.metodo.bookstoremanager.books.service;

import com.metodo.bookstoremanager.author.entity.Author;
import com.metodo.bookstoremanager.author.service.AuthorService;
import com.metodo.bookstoremanager.books.dto.BookRequestDTO;
import com.metodo.bookstoremanager.books.dto.BookResponseDTO;
import com.metodo.bookstoremanager.books.entity.Book;
import com.metodo.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.metodo.bookstoremanager.books.mapper.BookMapper;
import com.metodo.bookstoremanager.books.repository.BookRepository;
import com.metodo.bookstoremanager.publishers.entity.Publisher;
import com.metodo.bookstoremanager.publishers.service.PublisherService;
import com.metodo.bookstoremanager.users.dto.AuthenticatedUser;
import com.metodo.bookstoremanager.users.entity.User;
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

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verifyIfBookIsAlreadyRegistered(foundAuthenticatedUser, bookRequestDTO);

        Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        Book savedBook = bookRepository.save(bookToSave);

        return bookMapper.toDTO(savedBook);
    }

    private void verifyIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());
                } );
    }


}