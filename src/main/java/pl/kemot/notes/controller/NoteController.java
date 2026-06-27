package pl.kemot.notes.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kemot.notes.NoteService;
import pl.kemot.notes.model.Note;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> findAllNotes(){
        return noteService.findAllNotes();
    }

    @GetMapping("/{id}")
    public Note findNoteById(@PathVariable Long id){
        return noteService.findNoteById(id);
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody @Valid Note note){
        return noteService.addNote(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id){
        return noteService.deleteNoteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note){
        return noteService.updateNote(id, note);
    }
}
