package com.plopez.diceroller.microservice.authservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthUserDTO {
    @NotBlank(message = "Username must not be null.")
    private String userName;
    @NotBlank (message = "Password must not be null.")
    private String password;
    @NotBlank (message = "Role must not be null.")
    private String role;
}
