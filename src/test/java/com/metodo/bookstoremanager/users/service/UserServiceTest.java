package com.metodo.bookstoremanager.users.service;

import com.metodo.bookstoremanager.users.builder.UserDTOBuilder;
import com.metodo.bookstoremanager.users.dto.MessageDTO;
import com.metodo.bookstoremanager.users.dto.UserDTO;
import com.metodo.bookstoremanager.users.entity.User;
import com.metodo.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.metodo.bookstoremanager.users.mapper.UserMapper;
import com.metodo.bookstoremanager.users.repository.UserRepository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User alefchaves with 1 successfully created";

        String expectedUseremail = expectedCreatedUserDTO.getEmail();
        String expectedUserusername = expectedCreatedUserDTO.getUsername();
        when(userRepository.findByEmailOrUsername(expectedUseremail, expectedUserusername)).thenReturn(Optional.empty());
        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        MatcherAssert.assertThat(expectedCreationMessage, Matchers.is(Matchers.equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);

        String expectedDuplicatedEmail = expectedDuplicatedUser.getEmail();
        String expectedDuplicatedUsername = expectedDuplicatedUser.getUsername();

        when(userRepository.findByEmailOrUsername(expectedDuplicatedEmail, expectedDuplicatedUsername))
                .thenReturn(Optional.of(expectedDuplicatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));

    }
}
