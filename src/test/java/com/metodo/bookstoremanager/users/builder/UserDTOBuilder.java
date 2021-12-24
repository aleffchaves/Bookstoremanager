package com.metodo.bookstoremanager.users.builder;

import com.metodo.bookstoremanager.users.dto.UserDTO;
import com.metodo.bookstoremanager.users.enums.Gender;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    private Long id = 1L;

    @Builder.Default
    private String name = "Alef";

    @Builder.Default
    private int age = 27;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "alef@email.com";

    @Builder.Default
    private String username = "alef";

    @Builder.Default
    private String password = "123456";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1994, 11,13);

    public UserDTO buildUserDTO() {
        return new UserDTO(
                id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthDate
        );
    }

}
