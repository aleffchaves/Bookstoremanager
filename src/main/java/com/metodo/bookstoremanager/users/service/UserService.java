package com.metodo.bookstoremanager.users.service;

import com.metodo.bookstoremanager.users.dto.MessageDTO;
import com.metodo.bookstoremanager.users.dto.UserDTO;
import com.metodo.bookstoremanager.users.entity.User;
import com.metodo.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.metodo.bookstoremanager.users.exception.UserNotFoundException;
import com.metodo.bookstoremanager.users.mapper.UserMapper;
import com.metodo.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.*;


@Service
public class UserService {

    private static final UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser);
    }

    public void delete(Long id) {
        verifyIfExists(id);
        userRepository.deleteById(id);
    }

    private void verifyIfExists(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if(foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }

    private MessageDTO creationMessage(User createdUser) {

        String createdUsername = createdUser.getUsername();
        Long createdId = createdUser.getId();
        String createdUserMessage = format("User %s with %s successfully created", createdUsername, createdId);

        return MessageDTO.builder().message(createdUserMessage).build();
    }



}
