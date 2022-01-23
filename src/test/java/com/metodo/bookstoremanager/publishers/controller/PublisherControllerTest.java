package com.metodo.bookstoremanager.publishers.controller;

import com.metodo.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import com.metodo.bookstoremanager.publishers.service.PublisherService;
import com.metodo.bookstoremanager.utils.JsonConversionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.metodo.bookstoremanager.users.controller.UserControllerTest.USERS_API_URL_PATH;
import static com.metodo.bookstoremanager.utils.JsonConversionUtils.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        mockMvc.perform(post(PUBLISHERS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCreatedPublisherDTO)))
                        .andExpect(status()
                        .isBadRequest());

    }
}
