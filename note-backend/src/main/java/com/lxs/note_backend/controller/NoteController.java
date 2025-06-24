package com.lxs.note_backend.controller;

import com.lxs.note_backend.entity.Note;
import com.lxs.note_backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<?> createNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserNotes(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(noteService.getUserNotes(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Long id) {
        return ResponseEntity.of(noteService.getNoteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note note) {
        note.setId(id);
        return ResponseEntity.ok(noteService.updateNote(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
