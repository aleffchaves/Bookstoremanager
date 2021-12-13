package com.metodo.bookstoremanager.author.service;

import com.metodo.bookstoremanager.author.dto.AuthorDTO;
import com.metodo.bookstoremanager.author.entity.Author;
import com.metodo.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.metodo.bookstoremanager.author.mapper.AuthorMapper;
import com.metodo.bookstoremanager.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());
        Author authorToCreate = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {throw new AuthorAlreadyExistsException(authorName); });
    }
}
