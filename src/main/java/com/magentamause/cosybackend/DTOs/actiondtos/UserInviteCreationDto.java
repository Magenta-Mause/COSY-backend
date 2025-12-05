package com.magentamause.cosybackend.DTOs.actiondtos;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInviteCreationDto {
    private String username;
}
