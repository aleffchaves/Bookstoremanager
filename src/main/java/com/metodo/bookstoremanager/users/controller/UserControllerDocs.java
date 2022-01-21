package com.metodo.bookstoremanager.users.controller;

import com.metodo.bookstoremanager.users.dto.MessageDTO;
import com.metodo.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("system user management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required field, or an error validation field rules")
    })
    MessageDTO create(UserDTO userToCreateDTO);
}
