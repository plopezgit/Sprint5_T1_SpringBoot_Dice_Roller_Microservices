package com.plopez.diceroller.microservice.authservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Entity username identification.", example = "username", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Username must not be null.")
    private String userName;
    @Schema(description = "Auth user access password.", example = "password23", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank (message = "Password must not be null.")
    private String password;
    @Schema(description = "Auth user role.", example = "admin, editor", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank (message = "Role must not be null.")
    private String role;
}
