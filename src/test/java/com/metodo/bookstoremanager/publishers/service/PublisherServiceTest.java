package com.metodo.bookstoremanager.publishers.service;

import com.metodo.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import com.metodo.bookstoremanager.publishers.entity.Publisher;
import com.metodo.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.metodo.bookstoremanager.publishers.mapper.PublisherMapper;
import com.metodo.bookstoremanager.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTOBuilder();
        Publisher expectedPublisherToCreated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        var name = expectedPublisherToCreateDTO.getName();
        var code = expectedPublisherToCreateDTO.getCode();

        Mockito.when(publisherRepository.findByNameOrCode(name, code)).thenReturn(Optional.empty());
        Mockito.when(publisherRepository.save(expectedPublisherToCreated)).thenReturn(expectedPublisherToCreated);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenItShouldBeThrown() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTOBuilder();
        Publisher expectedPublisherDuplicated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        var name = expectedPublisherToCreateDTO.getName();
        var code = expectedPublisherToCreateDTO.getCode();

        Mockito.when(publisherRepository.findByNameOrCode(name, code))
                .thenReturn(Optional.of(expectedPublisherDuplicated));

        Assertions.assertThrows(PublisherAlreadyExistsException.class,
                () -> publisherService.create(expectedPublisherToCreateDTO));    }
}
