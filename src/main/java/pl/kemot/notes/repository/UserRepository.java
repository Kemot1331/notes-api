package pl.kemot.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kemot.notes.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
