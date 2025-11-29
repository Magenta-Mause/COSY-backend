package com.magentamause.cosybackend.entities.utility;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnvironmentVariableConfiguration {
    private String key;
    private String value;
}
