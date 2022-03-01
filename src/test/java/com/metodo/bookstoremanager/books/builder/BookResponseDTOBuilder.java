package com.metodo.bookstoremanager.books.builder;

import com.metodo.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.metodo.bookstoremanager.author.dto.AuthorDTO;
import com.metodo.bookstoremanager.books.dto.BookResponseDTO;
import com.metodo.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import com.metodo.bookstoremanager.users.builder.UserDTOBuilder;
import com.metodo.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookResponseDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Spring Boot";

    @Builder.Default
    private final String isbn = "978-3-16-148410-0";

    @Builder.Default
    private final PublisherDTO publisher = PublisherDTOBuilder.builder().build().buildPublisherDTOBuilder();

    @Builder.Default
    private AuthorDTO author = AuthorDTOBuilder.builder().build().buildAuthorDTO();

    @Builder.Default
    private final Integer pages = 200;

    @Builder.Default
    private final Integer chapters = 10;

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookResponseDTO buildRequestBookDTO() {
        return new BookResponseDTO(
                id,
                name,
                isbn,
                pages,
                chapters,
                author,
                publisher
                );
    }
}
