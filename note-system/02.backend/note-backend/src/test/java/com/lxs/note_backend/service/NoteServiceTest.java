package com.lxs.note_backend.service;

import com.lxs.note_backend.entity.Note;
import com.lxs.note_backend.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void testCreateNote() {
        Note note = new Note();
        note.setTitle("Test");
        note.setContent("Content");

        Mockito.when(noteRepository.save(note)).thenReturn(note);

        Note savedNote = noteService.createNote(note);

        assertEquals("Test", savedNote.getTitle());
        verify(noteRepository).save(note);
    }


    @Test
    void testGetNoteById() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Note 1");

        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        Optional<Note> result = noteService.getNoteById(1L);
        assertTrue(result.isPresent());
        assertEquals("Note 1", result.get().getTitle());
    }
}
