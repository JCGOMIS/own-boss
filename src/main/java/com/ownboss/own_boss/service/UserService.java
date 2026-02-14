package com.ownboss.own_boss.service;

import com.ownboss.own_boss.domain.User;
import com.ownboss.own_boss.dtos.CreateUserRequest;
import com.ownboss.own_boss.dtos.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface UserService {

    public UserResponse createUser(CreateUserRequest request);

    public List<UserResponse> findUsers();

    public UserResponse findUserByEmail(String email);

}
