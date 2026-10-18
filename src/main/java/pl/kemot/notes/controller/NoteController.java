package pl.kemot.notes.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kemot.notes.model.note.dto.NoteRequestDto;
import pl.kemot.notes.model.note.dto.NoteResponseDto;
import pl.kemot.notes.service.NoteService;
import pl.kemot.notes.model.note.Note;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteResponseDto> findAllNotes(){
        return noteService.findAllNotes();
    }

    @GetMapping("/{id}")
    public NoteResponseDto findNoteById(@PathVariable Long id){
        return noteService.findNoteById(id);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> addNote(@RequestBody @Valid NoteRequestDto noteDto){
        NoteResponseDto createdNote = noteService.addNote(noteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id){
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequestDto noteDto){
        NoteResponseDto updatedNote = noteService.updateNote(id, noteDto);
        return ResponseEntity.ok(updatedNote);
    }
    @GetMapping("/search")
    public List<NoteResponseDto> findByTitleIgnoringCase(@RequestParam String keyword){
        return noteService.findByTitleIgnoringCase(keyword);
    }
}
