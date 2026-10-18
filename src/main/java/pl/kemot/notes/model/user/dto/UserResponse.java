package pl.kemot.notes.model.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}
