package pl.kemot.notes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.kemot.notes.model.Note;
import pl.kemot.notes.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    public Note findNoteById(Long id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Note> addNote(Note note){
        return ResponseEntity.status(HttpStatus.CREATED).body(noteRepository.save(note));
    }

    public ResponseEntity<Void> deleteNoteById(Long id){
        Optional<Note> noteToDelete = noteRepository.findById(id);

        if(noteToDelete.isPresent()){
            noteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Note> updateNote(Long id, Note note){
        Note updateNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());

        noteRepository.save(updateNote);
        return ResponseEntity.status(HttpStatus.OK).body(updateNote);
    }
}
