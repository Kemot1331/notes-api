package pl.kemot.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kemot.notes.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
