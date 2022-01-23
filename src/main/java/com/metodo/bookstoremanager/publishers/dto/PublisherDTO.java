package com.metodo.bookstoremanager.publishers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private Long id;

    @NotNull
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @NotNull
    @Size(max = 255)
    private String code;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate foundationDate;

}
