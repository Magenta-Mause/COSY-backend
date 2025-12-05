package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.actiondtos.UserCreationDto;
import com.magentamause.cosybackend.DTOs.actiondtos.UserInviteCreationDto;
import com.magentamause.cosybackend.DTOs.entitydtos.UserInviteDto;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.entities.UserInviteEntity;
import com.magentamause.cosybackend.services.SecurityContextService;
import com.magentamause.cosybackend.services.UserInviteService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-invites")
public class UserInviteController {

    private final UserInviteService userInviteService;
    private final SecurityContextService securityContextService;

    @GetMapping
    public ResponseEntity<List<UserInviteDto>> getAllUserInvites() {
        securityContextService.assertUserHasRole(UserEntity.Role.OWNER);
        return ResponseEntity.ok(
                userInviteService.getAllInvites().stream()
                        .map(UserInviteEntity::convertToDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{secretKey}")
    public ResponseEntity<UserInviteDto> getUserInvite(
            @PathVariable("secretKey") String secretToken) {
        return ResponseEntity.ok(
                userInviteService.getInviteBySecretKey(secretToken).convertToDto());
    }

    @PostMapping
    public ResponseEntity<UserInviteDto> createInvite(
            @RequestBody UserInviteCreationDto userInviteCreationDto) {
        securityContextService.assertUserHasRole(UserEntity.Role.OWNER);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userInviteService
                                .createInvite(
                                        securityContextService.getUserId(),
                                        userInviteCreationDto.getUsername())
                                .convertToDto());
    }

    @PostMapping("/use/{secretKey}")
    public ResponseEntity<UserEntity> useInvite(
            @PathVariable("secretKey") String secretToken, @RequestBody UserCreationDto user) {
        return ResponseEntity.ok(
                userInviteService.useInvite(secretToken, user.getUsername(), user.getPassword()));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> revokeInvite(@PathVariable String uuid) {
        userInviteService.revokeInvite(uuid);
        return ResponseEntity.noContent().build();
    }
}
