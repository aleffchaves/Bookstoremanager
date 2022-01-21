package com.metodo.bookstoremanager.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.metodo.bookstoremanager.author.dto.AuthorDTO;
import com.metodo.bookstoremanager.users.dto.UserDTO;

public class JsonConversionUtils {

    public static String asJsonString(AuthorDTO expectedCreatedAuthorDTO) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.writeValueAsString(expectedCreatedAuthorDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String asJsonString(UserDTO expectedUserToCreateDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.writeValueAsString(expectedUserToCreateDTO);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
