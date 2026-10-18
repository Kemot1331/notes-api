package pl.kemot.notes.model.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email cannot be empty")
    @Size(max = 254)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(max = 60)
    private String password;
}
