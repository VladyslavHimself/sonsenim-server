package com.sonsenim.sonsenimbackend.mappers;

import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.model.dto.LocalUserDTO;

import java.util.Optional;

public class LocalUserMapper {
    public static LocalUserDTO toDTO(Optional<LocalUser> localUser) {
        if (localUser.isPresent()) {
            LocalUserDTO dto = new LocalUserDTO();
            dto.setId(localUser.get().getId());
            dto.setUsername(localUser.get().getUsername());
            dto.setEmail(localUser.get().getEmail());
            return dto;
        }
        else {
            System.out.println("User not found");
            return null;
        }
    }
}
