package pl.kemot.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kemot.notes.model.note.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
//    List<Note> findByTitleContainingIgnoreCase(String title);
    List<Note> findByuserId(Long userId);
    List<Note> findByTitleContainingIgnoreCaseAndUserId(String title, Long userId);
    Optional<Note> findByIdAndUserId(Long noteId, Long userId);
}

