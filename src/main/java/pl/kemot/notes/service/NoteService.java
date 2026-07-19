package pl.kemot.notes.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.kemot.notes.model.note.Note;
import pl.kemot.notes.model.note.dto.NoteRequestDto;
import pl.kemot.notes.model.note.dto.NoteResponseDto;
import pl.kemot.notes.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteResponseDto> findAllNotes() {
        return noteRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    public NoteResponseDto findNoteById(Long id){
        return noteRepository.findById(id)
                .map(this::convertToResponseDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public NoteResponseDto addNote(NoteRequestDto noteDto){
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setDescription(noteDto.getDescription());

        return convertToResponseDto(noteRepository.save(note));
    }

    public void deleteNoteById(Long id){
        if(!noteRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        noteRepository.deleteById(id);
    }

    public NoteResponseDto updateNote(Long id, NoteRequestDto noteDto){
        Note updateNote = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateNote.setTitle(noteDto.getTitle());
        updateNote.setDescription(noteDto.getDescription());
        updateNote.setUpdatedAt(LocalDateTime.now());

        return convertToResponseDto(noteRepository.save(updateNote));
    }

    public List<NoteResponseDto> findByTitleIgnoringCase(String keyword){
        return noteRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    private NoteResponseDto convertToResponseDto(Note note){
        NoteResponseDto dto = new NoteResponseDto();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setDescription(note.getDescription());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        dto.setIsPinned(note.getIsPinned());
        return dto;
    }
}
