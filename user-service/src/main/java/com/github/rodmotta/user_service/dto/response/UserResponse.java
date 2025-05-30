package com.github.rodmotta.user_service.dto.response;

import com.github.rodmotta.user_service.entity.UserEntity;

public record UserResponse(
        Long id,
        String name
) {
    public UserResponse(UserEntity userEntity) {
        this(
                userEntity.getId(),
                userEntity.getName()
        );
    }
}
