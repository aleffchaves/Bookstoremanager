package com.metodo.bookstoremanager.users.builder;

import com.metodo.bookstoremanager.users.dto.UserDTO;
import com.metodo.bookstoremanager.users.enums.Gender;
import com.metodo.bookstoremanager.users.enums.Role;
import lombok.Builder;
import lombok.Builder.Default;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Default
    private Long id = 1L;

    @Default
    private String name = "Alef";

    @Default
    private int age = 27;

    @Default
    private Gender gender = Gender.MALE;

    @Default
    private String email = "alef@email.com";

    @Default
    private String username = "alefchaves";

    @Default
    private String password = "123456";

    @Default
    private LocalDate birthDate = LocalDate.of(1994, 11,13);

    @Default
    private Role role = Role.USER;

    public UserDTO buildUserDTO() {
        return new UserDTO(
                id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthDate,
                role
        );
    }

}
