package com.ownboss.own_boss.dtos;

import com.ownboss.own_boss.domain.Role;

public record UserResponse(
        Long id, String email, Role role
) {
}
