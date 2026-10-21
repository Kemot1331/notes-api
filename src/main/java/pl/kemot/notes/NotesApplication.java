package pl.kemot.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.kemot.notes.service.JwtService;

@SpringBootApplication
public class NotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesApplication.class, args);
    }

}
