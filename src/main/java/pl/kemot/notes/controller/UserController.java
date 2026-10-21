package pl.kemot.notes.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kemot.notes.model.user.dto.AuthResponse;
import pl.kemot.notes.model.user.dto.LoginRequest;
import pl.kemot.notes.model.user.dto.RegisterRequest;
import pl.kemot.notes.model.user.dto.UserResponse;
import pl.kemot.notes.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse createdUser = userService.registerUser(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginRequest));
    }
}
