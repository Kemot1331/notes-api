package pl.kemot.notes.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.kemot.notes.model.user.User;
import pl.kemot.notes.model.user.dto.AuthResponse;
import pl.kemot.notes.model.user.dto.LoginRequest;
import pl.kemot.notes.model.user.dto.RegisterRequest;
import pl.kemot.notes.model.user.dto.UserResponse;
import pl.kemot.notes.repository.UserRepository;

import java.util.Locale;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    private String mailNormalization(String email){
        return email.toLowerCase(Locale.ROOT).trim();
    }

    public UserResponse registerUser(RegisterRequest registerRequest) {
        String emailToRegister = mailNormalization(registerRequest.getEmail());

        if (userRepository.existsByEmail(emailToRegister)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername().trim());
        user.setEmail(emailToRegister);
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        return convertUserToUserResponse(userRepository.save(user));
    }

    private UserResponse convertUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        String emailToLogin = mailNormalization(loginRequest.getEmail());

        User user = userRepository.findByEmail(emailToLogin)
                .orElseThrow(this::responseStatusExceptionToLogin);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw responseStatusExceptionToLogin();
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtService.generateToken(user.getId()));
        authResponse.setUser(convertUserToUserResponse(user));
        return authResponse;
    }

    private ResponseStatusException responseStatusExceptionToLogin() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong email or password");
    }
}
