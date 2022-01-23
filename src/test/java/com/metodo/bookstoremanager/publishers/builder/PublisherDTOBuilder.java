package com.metodo.bookstoremanager.publishers.builder;

import com.metodo.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    private final String name = "MeiraPublisher";

    private final String code = "ALEF1234";

    private final LocalDate foundationDate = LocalDate.of(2022, 8, 15);

    public PublisherDTO buildPublisherDTOBuilder() {
        return new PublisherDTO(id, name, code, foundationDate);
    }
}
