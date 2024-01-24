package com.plopez.diceroller.microservice.authservice.security;

import com.plopez.diceroller.microservice.authservice.model.dto.HttpRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "admin-paths")
public class AuthRouteValidator {

    private List<HttpRequestDTO> paths;

    public boolean isAdminPath(HttpRequestDTO httpRequestDTO) {
        return paths.stream().anyMatch(path ->
                Pattern.matches(path.getUri(), httpRequestDTO.getUri())
                && path.getMethod().equals(httpRequestDTO.getMethod()));
    }
}
