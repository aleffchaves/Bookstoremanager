package com.metodo.bookstoremanager.users.service;

import com.metodo.bookstoremanager.users.builder.UserDTOBuilder;
import com.metodo.bookstoremanager.users.dto.UserDTO;
import com.metodo.bookstoremanager.users.entity.User;
import com.metodo.bookstoremanager.users.mapper.UserMapper;
import com.metodo.bookstoremanager.users.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }


    @Test
    void whenUserIsInformedThenUserShouldBeReturned() {
        UserDTO expectedFoundUserDTO =  userDTOBuilder.buildUserDTO();
        User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO);
        SimpleGrantedAuthority expectedUserRole = new SimpleGrantedAuthority
                (
                "ROLE_" + expectedFoundUserDTO.getRole().getDescription()
                );
        String expectedUsername = expectedFoundUserDTO.getUsername();


        when(userRepository.findByUsername(expectedFoundUserDTO.getUsername()))
                .thenReturn(Optional.of(expectedFoundUser));


       UserDetails userDetails = authenticationService.loadUserByUsername(expectedUsername);

        assertThat(userDetails.getUsername(), Matchers.is(Matchers.equalTo(expectedFoundUser.getUsername())));
        assertThat(userDetails.getPassword(), Matchers.is(Matchers.equalTo(expectedFoundUser.getPassword())));
        assertTrue(userDetails.getAuthorities().contains(expectedUserRole));

    }

    @Test
    void whenInvalidUserIdIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
        String expectedUsername = expectedFoundUserDTO.getUsername();

        when(userRepository.findByUsername(expectedUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(expectedUsername));
    }

}
