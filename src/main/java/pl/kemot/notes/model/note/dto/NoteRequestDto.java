package pl.kemot.notes.model.note.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {

    @NotBlank(message = "Title can't be empty")
    private String title;

    @Size(max = 200)
    private String description;

    private Boolean isPinned;
}
