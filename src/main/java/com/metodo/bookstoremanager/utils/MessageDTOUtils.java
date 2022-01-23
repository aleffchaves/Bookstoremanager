package com.metodo.bookstoremanager.utils;

import com.metodo.bookstoremanager.users.dto.MessageDTO;
import com.metodo.bookstoremanager.users.entity.User;

import static java.lang.String.format;

public class MessageDTOUtils {

    public MessageDTOUtils() {
    }

    public static MessageDTO creationMessage(User createdUser) {
        return returnMessage(createdUser, "created");
    }

    public static MessageDTO updateMessage(User updateUser) {
        return returnMessage(updateUser, "updated");
    }

    public static MessageDTO returnMessage(User updateUser, String action) {
        String createdUsername = updateUser.getUsername();
        Long createdId = updateUser.getId();
        String createdUserMessage = format("User %s with %s successfully %s", createdUsername, createdId, action);
        return MessageDTO.builder().message(createdUserMessage).build();
    }
}
