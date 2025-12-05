package com.magentamause.cosybackend.entities;

import com.magentamause.cosybackend.DTOs.entitydtos.UserInviteDto;
import jakarta.persistence.*;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInviteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String username;

    @Column(unique = true, nullable = false)
    private String secretKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invited_by_id")
    private UserEntity invitedBy;

    public UserInviteDto convertToDto() {
        return UserInviteDto.builder()
                .uuid(this.getUuid())
                .username(this.getUsername())
                .invitedBy(this.getInvitedBy().getUuid())
                .secretKey(this.getSecretKey())
                .build();
    }
}
