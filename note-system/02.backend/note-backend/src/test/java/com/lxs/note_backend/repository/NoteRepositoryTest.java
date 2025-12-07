package com.lxs.note_backend.repository;

import com.lxs.note_backend.entity.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Test
    public void testSaveAndFindAll() {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setContent("Test Content");

        noteRepository.save(note);

        List<Note> notes = noteRepository.findAll();
        assertThat(notes).isNotEmpty();
        assertThat(notes.get(0).getTitle()).isEqualTo("Test Title");
    }
}
