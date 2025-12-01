package com.magentamause.cosybackend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "login_entry")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntry {
    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @Column private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private UserEntity user;
}
