package com.metodo.bookstoremanager.users.mapper;

import com.metodo.bookstoremanager.users.dto.UserDTO;
import com.metodo.bookstoremanager.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);
    User toDTO(User user);
}
