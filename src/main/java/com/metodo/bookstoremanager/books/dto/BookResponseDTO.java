package com.metodo.bookstoremanager.books.dto;

import com.metodo.bookstoremanager.author.dto.AuthorDTO;
import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String isbn;

    private Long pages;

    private Long chapters;

    private AuthorDTO author;

    private PublisherDTO publisher;
}
