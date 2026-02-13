package com.ownboss.own_boss.service.impl;

import com.ownboss.own_boss.domain.Role;
import com.ownboss.own_boss.domain.User;
import com.ownboss.own_boss.dtos.CreateUserRequest;
import com.ownboss.own_boss.dtos.UserResponse;
import com.ownboss.own_boss.exceptions.UserNotFoundException;
import com.ownboss.own_boss.mappers.UserMapper;
import com.ownboss.own_boss.repositories.UserRepository;
import com.ownboss.own_boss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if(repository.existsByEmail(request.email())) throw  new IllegalArgumentException("Email already exits");

        User user = mapper.toEntity(request);
        user.setPassword(encoder.encode(request.password()));
        user.setRole(Role.USER);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public List<UserResponse> findUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserResponse findUserByEmail(String email) {

        User user = repository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException(email));
        return mapper.toDto(user);
    }
}
