package pl.kemot.notes.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.kemot.notes.model.note.dto.NoteRequestDto;
import pl.kemot.notes.model.note.dto.NoteResponseDto;
import pl.kemot.notes.service.NoteService;

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
    public List<NoteResponseDto> findAllNotes(@AuthenticationPrincipal Long userId){
        return noteService.findAllNotes(userId);
    }

    @GetMapping("/{id}")
    public NoteResponseDto findNoteById(@PathVariable Long id,  @AuthenticationPrincipal Long userId){
        return noteService.findNoteById(id, userId);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> addNote(@RequestBody @Valid NoteRequestDto noteDto, @AuthenticationPrincipal Long userId){
        NoteResponseDto createdNote = noteService.addNote(noteDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id, @AuthenticationPrincipal Long userId){
        noteService.deleteNoteById(id,  userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long id,
                                                      @Valid @RequestBody NoteRequestDto noteDto,
                                                      @AuthenticationPrincipal Long userId ){
        NoteResponseDto updatedNote = noteService.updateNote(id, noteDto,  userId);
        return ResponseEntity.ok(updatedNote);
    }
    @GetMapping("/search")
    public List<NoteResponseDto> findByTitleIgnoringCase(@RequestParam String keyword, @AuthenticationPrincipal Long userId){
        return noteService.findByTitleIgnoringCase(keyword, userId);
    }
}
