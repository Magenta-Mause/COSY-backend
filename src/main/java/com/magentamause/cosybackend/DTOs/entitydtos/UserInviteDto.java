package com.magentamause.cosybackend.DTOs.entitydtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import lombok.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInviteDto {
    private String uuid;
    private String username;
    private String invitedBy;
    private String secretKey;
    private Instant createdAt;
}
