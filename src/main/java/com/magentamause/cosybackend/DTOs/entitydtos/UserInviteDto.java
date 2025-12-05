package com.magentamause.cosybackend.DTOs.entitydtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

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
}
