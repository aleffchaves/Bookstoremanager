package com.metodo.bookstoremanager.users.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtResponse {

    private final String jwtToken;

}
