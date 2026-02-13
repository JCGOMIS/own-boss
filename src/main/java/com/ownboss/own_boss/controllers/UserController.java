package com.ownboss.own_boss.controllers;

import com.ownboss.own_boss.domain.User;
import com.ownboss.own_boss.dtos.CreateUserRequest;
import com.ownboss.own_boss.dtos.UserResponse;
import com.ownboss.own_boss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserService service;

    @GetMapping

    public ResponseEntity<List<UserResponse>> getAllUsers(){

        List<UserResponse> users = service.findUsers();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(users);
    }
    @PostMapping

    public  ResponseEntity<UserResponse> createUser( @RequestBody CreateUserRequest request ){

    UserResponse createdUser = service.createUser(request);

      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

    }
    @GetMapping("/{email}")

    public ResponseEntity<UserResponse> findUserByEmail(@PathVariable String email){

        return  ResponseEntity.ok(service.findUserByEmail(email));
    }

}
