package pl.kemot.notes.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.kemot.notes.model.Note;
import pl.kemot.notes.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping
    public List<Note> findAllNotes(){
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Note findNoteById(@PathVariable Long id){
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody @Valid Note note){
        return ResponseEntity.status(HttpStatus.CREATED).body(noteRepository.save(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id){
        Optional<Note> noteToDelete = noteRepository.findById(id);
        if(noteToDelete.isPresent()){
            noteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note){
        Note updateNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        updateNote.setTitle(note.getTitle());
        updateNote.setDescription(note.getDescription());

        noteRepository.save(updateNote);

        return ResponseEntity.status(HttpStatus.OK).body(updateNote);
    }
}
