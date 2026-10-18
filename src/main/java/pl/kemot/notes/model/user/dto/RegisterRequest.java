package pl.kemot.notes.model.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Email can't be empty")
    @Email
    @Size(min = 3, max = 254, message = "The email is too big or small")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 64, message = "The password is too big or small")
    @Column(name = "password_hash")
    private String password;
}
