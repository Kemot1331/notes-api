package pl.kemot.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kemot.notes.model.note.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleContainingIgnoreCase(String title);
}
