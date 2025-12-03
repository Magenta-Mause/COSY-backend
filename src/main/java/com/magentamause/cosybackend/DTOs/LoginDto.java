package com.magentamause.cosybackend.DTOs;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDto {
    @NonNull private String username;
    @NonNull private String password;
}
