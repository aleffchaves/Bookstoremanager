package com.metodo.bookstoremanager.books.Controller;

import com.metodo.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.metodo.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.metodo.bookstoremanager.books.controller.BookController;
import com.metodo.bookstoremanager.books.dto.BookRequestDTO;
import com.metodo.bookstoremanager.books.dto.BookResponseDTO;
import com.metodo.bookstoremanager.books.service.BookService;
import com.metodo.bookstoremanager.users.dto.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.metodo.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private static final String BOOKS_API_URL_PATH = "/api/v1/books";

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .addFilters()
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildResponseBookDTO();

        when(bookService.create(any(AuthenticatedUser.class), eq(expectedBookToCreateDTO)))
                .thenReturn(expectedCreatedBookDTO);


        mockMvc.perform(MockMvcRequestBuilders.post(BOOKS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedBookToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())));
    }

    @Test
    void whenPOSTIsCalledWithOutRequiredFieldThenBadRequestStatusShouldBeReturned () throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        expectedBookToCreateDTO.setIsbn(null);

        mockMvc.perform(MockMvcRequestBuilders.post(BOOKS_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedBookToCreateDTO)))
                        .andExpect(status().isBadRequest());
    }
}
