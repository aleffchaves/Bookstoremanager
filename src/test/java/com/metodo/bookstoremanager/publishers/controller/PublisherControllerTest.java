package com.metodo.bookstoremanager.publishers.controller;

import com.metodo.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import com.metodo.bookstoremanager.publishers.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.metodo.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    private final static String PUBLISHERS_API_URL_PATH = "/api/v1/publishers";

    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController PublisherController;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(PublisherController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTOBuilder();

        when(publisherService.create(expectedCreatedPublisherDTO)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(post(PUBLISHERS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenPOSTIsCalledWithOutRequiredFieldThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTOBuilder();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHERS_API_URL_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCreatedPublisherDTO))).andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWhitValidIdIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTOBuilder();
        Long expectedPublisherFoundDTOId = expectedPublisherFoundDTO.getId();

        Mockito.when(publisherService.findById(expectedPublisherFoundDTOId)).thenReturn(expectedPublisherFoundDTO);

        mockMvc.perform(get(PUBLISHERS_API_URL_PATH + "/" + expectedPublisherFoundDTOId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedPublisherFoundDTOId.intValue())))
                .andExpect(jsonPath("$.name", is(expectedPublisherFoundDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedPublisherFoundDTO.getCode())));
    }

    @Test
    void whenGetListCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedPublisherListDTO = publisherDTOBuilder.buildPublisherDTOBuilder();

        when(publisherService.findAll()).thenReturn(Collections.singletonList(expectedPublisherListDTO));

        mockMvc.perform(get(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedPublisherListDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedPublisherListDTO.getName())))
                .andExpect(jsonPath("$[0].code", is(expectedPublisherListDTO.getCode())));
    }

    @Test
    void whenDELETEWithValidIdIsCalledThenNoContentShouldBeInformed() throws Exception {
        PublisherDTO expectedPublisherToDeleteDTO = publisherDTOBuilder.buildPublisherDTOBuilder();
        var expectedPublisherIdToDeleted = expectedPublisherToDeleteDTO.getId();

        doNothing().when(publisherService).delete(expectedPublisherIdToDeleted);

        mockMvc.perform(delete(PUBLISHERS_API_URL_PATH + "/" + expectedPublisherIdToDeleted)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}
