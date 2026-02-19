package com.ownboss.own_boss;

import com.ownboss.own_boss.domain.Role;
import com.ownboss.own_boss.domain.User;
import com.ownboss.own_boss.dtos.CreateUserRequest;
import com.ownboss.own_boss.dtos.UserResponse;
import com.ownboss.own_boss.exceptions.UserNotFoundException;
import com.ownboss.own_boss.mappers.UserMapper;
import com.ownboss.own_boss.repositories.UserRepository;
import com.ownboss.own_boss.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    // ===== createUser =====

    @Test
    void createUser_shouldReturnUserResponse_whenEmailIsNew() {
        CreateUserRequest request = new CreateUserRequest("jean@mail.com", "password123");
        User user = new User();
        User savedUser = new User();
        UserResponse expectedResponse = new UserResponse(1L, "jean@mail.com",  Role.USER);

        when(repository.existsByEmail(request.email())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(user);
        when(encoder.encode(request.password())).thenReturn("encodedPassword");
        when(repository.save(user)).thenReturn(savedUser);
        when(mapper.toDto(savedUser)).thenReturn(expectedResponse);

        UserResponse result = userService.createUser(request);

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getPassword()).isEqualTo("encodedPassword");

        verify(repository).existsByEmail(request.email());
        verify(repository).save(user);
        verify(encoder).encode(request.password());
    }

    @Test
    void createUser_shouldThrowException_whenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest("jean@mail.com", "password123");

        when(repository.existsByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exits");

        verify(repository, never()).save(any());
    }

    // ===== findUsers =====

    @Test
    void findUsers_shouldReturnListOfUserResponses() {
        User user1 = new User();
        User user2 = new User();
        UserResponse response1 = new UserResponse(1L, "a@mail.com",  Role.USER);
        UserResponse response2 = new UserResponse(2L, "b@mail.com", Role.USER);

        when(repository.findAll()).thenReturn(List.of(user1, user2));
        when(mapper.toDto(user1)).thenReturn(response1);
        when(mapper.toDto(user2)).thenReturn(response2);

        List<UserResponse> result = userService.findUsers();

        assertThat(result).hasSize(2).containsExactly(response1, response2);
    }

    @Test
    void findUsers_shouldReturnEmptyList_whenNoUsers() {
        when(repository.findAll()).thenReturn(List.of());

        List<UserResponse> result = userService.findUsers();

        assertThat(result).isEmpty();
    }

    // ===== findUserByEmail =====

    @Test
    void findUserByEmail_shouldReturnUserResponse_whenUserExists() {
        String email = "jean@mail.com";
        User user = new User();
        UserResponse expectedResponse = new UserResponse(1L, email,  Role.USER);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(expectedResponse);

        UserResponse result = userService.findUserByEmail(email);

        assertThat(result).isEqualTo(expectedResponse);
    }

    @Test
    void findUserByEmail_shouldThrowUserNotFoundException_whenUserNotFound() {
        String email = "unknown@mail.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUserByEmail(email))
                .isInstanceOf(UserNotFoundException.class);

        verify(mapper, never()).toDto(any(User.class));
    }
}
