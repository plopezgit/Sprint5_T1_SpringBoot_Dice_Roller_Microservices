package com.plopez.diceroller.microservice.authservice.model.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection="authUsers")
public class AuthUser {
    @NotBlank(message = "Username must not be null.")
    private String userName;
    @NotBlank (message = "Password must not be null.")
    private String password;
    @NotBlank (message = "Role must not be null.")
    private String role;
}
