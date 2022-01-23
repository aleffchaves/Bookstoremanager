package com.metodo.bookstoremanager.publishers.exception;

import javax.persistence.EntityExistsException;

import static java.lang.String.format;

public class PublisherAlreadyExistsException extends EntityExistsException {
    public PublisherAlreadyExistsException(String name, String code) {
        super(format("Publisher with name %s code already exists!", name, code));
    }
}
