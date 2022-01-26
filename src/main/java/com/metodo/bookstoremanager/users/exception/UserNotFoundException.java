package com.metodo.bookstoremanager.users.exception;

import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User with %s not exists", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with %s not exists", username));
    }
}
