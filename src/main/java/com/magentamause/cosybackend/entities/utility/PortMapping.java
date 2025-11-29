package com.magentamause.cosybackend.entities.utility;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PortMapping {
    private int instancePort;
    private int containerPort;
}
