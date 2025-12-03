package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.UserEntityDTO;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.services.UserEntityService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-entity")
public class UserEntityController {

    private final UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getAllUserEntities() {
        List<UserEntity> users = userEntityService.getAllUsers();
        List<UserEntityDTO> userDTOs =
                users.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserEntityDTO> getUserEntity(@PathVariable String uuid) {
        UserEntity user = userEntityService.getUserByUuid(uuid);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserEntity(@PathVariable String uuid) {
        userEntityService.deleteUserByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

    private UserEntityDTO convertToDTO(UserEntity user) {
        return UserEntityDTO.builder().username(user.getUsername()).role(user.getRole()).build();
    }
}
