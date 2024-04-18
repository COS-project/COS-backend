package com.cos.cercat.apis.user.dto.response;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.dto.UserDTO;

public record UserResponse(
        Long userId,
        String nickname,
        String email,
        String profileImage
    ) {
    public static UserResponse fromEntity(UserEntity entity) {
        return fromDTO(UserDTO.fromEntity(entity));
    }

    public static UserResponse fromDTO(UserDTO dto) {
        return new UserResponse(
                dto.getId(),
                dto.getNickname(),
                dto.getEmail(),
                dto.getProfileImage()
        );
    }

}
