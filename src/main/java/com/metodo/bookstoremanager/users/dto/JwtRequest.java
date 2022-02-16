package com.metodo.bookstoremanager.users.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    @NotEmpty
    @NotNull
    private String user;

    @NotEmpty
    @NotNull
    private String password;


}
