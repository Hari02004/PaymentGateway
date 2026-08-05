package org.JamesWeb.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotNull(message = "User email cannot be empty")
    @Email(message = "Email is required")
    private String username;
    @NotNull(message = "password cannot be empty")
    private String password;
}
