package com.metodo.bookstoremanager.author.service;

import com.metodo.bookstoremanager.author.dto.AuthorDTO;
import com.metodo.bookstoremanager.author.entity.Author;
import com.metodo.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.metodo.bookstoremanager.author.exception.AuthorNotFoundException;
import com.metodo.bookstoremanager.author.mapper.AuthorMapper;
import com.metodo.bookstoremanager.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Autowired
    private final AuthorRepository authorRepository;

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

    public AuthorDTO findById(Long id) {
        Author foundAuthor = verifyAndGetIfExists(id);
        return authorMapper.toDTO(foundAuthor);
    }

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {
                    throw new AuthorAlreadyExistsException(authorName);
                });
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        authorRepository.deleteById(id);
    }

    public Author verifyAndGetIfExists(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }


}