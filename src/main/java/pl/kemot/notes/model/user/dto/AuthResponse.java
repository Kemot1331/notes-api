package pl.kemot.notes.model.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private UserResponse user;
}
