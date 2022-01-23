package com.metodo.bookstoremanager.publishers.service;

import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import com.metodo.bookstoremanager.publishers.entity.Publisher;
import com.metodo.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.metodo.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.metodo.bookstoremanager.publishers.mapper.PublisherMapper;
import com.metodo.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if (duplicatedPublisher.isPresent()) {
             throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
