package com.metodo.bookstoremanager.books.repository;

import com.metodo.bookstoremanager.books.entity.Book;
import com.metodo.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameAndIsbnAndUser(String name, String isbn, User user);
    Optional<Book> findByIdAndUser(Long id, User user);
    List<Book> findAllByUser(User user);
    void deleteByIdAndUser(Long id, User user);
}
