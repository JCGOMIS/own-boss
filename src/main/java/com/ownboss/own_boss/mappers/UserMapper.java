package com.ownboss.own_boss.mappers;

import com.ownboss.own_boss.domain.User;
import com.ownboss.own_boss.dtos.CreateUserRequest;
import com.ownboss.own_boss.dtos.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toDto(User user);
    User toEntity(CreateUserRequest request);
}
