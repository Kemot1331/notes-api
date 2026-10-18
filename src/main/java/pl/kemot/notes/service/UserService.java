package pl.kemot.notes.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.kemot.notes.model.user.User;
import pl.kemot.notes.model.user.dto.RegisterRequest;
import pl.kemot.notes.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest registerRequest) {
        String emailToRegister = registerRequest.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(emailToRegister)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername().trim());
        user.setEmail(emailToRegister);
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);
    }
}
