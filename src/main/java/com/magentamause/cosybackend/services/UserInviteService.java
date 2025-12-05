package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.entities.UserInviteEntity;
import com.magentamause.cosybackend.repositories.UserInviteRepository;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInviteService {
    private final char[] POSSIBLE_CHARACTERS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private final int KEY_LENGTH = 10;
    private final UserInviteRepository userInviteRepository;
    private final UserEntityService userEntityService;

    public List<UserInviteEntity> getAllInvites() {
        return userInviteRepository.findAll();
    }

    public void revokeInvite(String inviteUuid) {
        userInviteRepository.deleteById(inviteUuid);
    }

    public UserInviteEntity createInvite(String ownerCreationId, String username) {
        UserInviteEntity invite =
                UserInviteEntity.builder()
                        .invitedBy(userEntityService.getUserByUuid(ownerCreationId))
                        .secretKey(generateRandomKey())
                        .username(username)
                        .build();

        return userInviteRepository.save(invite);
    }

    public UserInviteEntity getInviteBySecretKey(String secretToken) {
        return userInviteRepository
                .findBySecretKey(secretToken)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Invite not found"));
    }

    public UserInviteEntity getInviteByUuid(String inviteUuid) {
        return userInviteRepository
                .findById(inviteUuid)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Invite not found"));
    }

    public UserEntity useInvite(String secretKey, String userName, String password) {
        UserInviteEntity invite = getInviteBySecretKey(secretKey);
        UserEntity.UserEntityBuilder userBuilder =
                UserEntity.builder()
                        .role(UserEntity.Role.QUOTA_USER)
                        .password(password)
                        .defaultPasswordReset(true);
        if (Objects.isNull(invite.getUsername())) {
            userBuilder.username(userName);
        } else {
            userBuilder.username(invite.getUsername());
        }
        UserEntity user = userEntityService.saveUserEntity(userBuilder.build());
        log.info("Invite used [{}] used for user {}", secretKey, user.getUsername());
        return user;
    }

    private String generateRandomKey() {
        Random random = new Random();
        return random.ints(0, POSSIBLE_CHARACTERS.length)
                .limit(KEY_LENGTH)
                .map(i -> POSSIBLE_CHARACTERS[i])
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
