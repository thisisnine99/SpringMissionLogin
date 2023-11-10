package com.korea.article.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> getList() {
        return noteRepository.findByParentId(null);
    }

    public List<Note> getAllLIst() {
        return noteRepository.findAll();
    }

    public Note createNote() {
        Note note = new Note();
        note.setCreateDate(LocalDateTime.now());
        note.setTitle("새노트");
        noteRepository.save(note);
        return note;
    }

    public Note findById(Integer id) {
        return noteRepository.findById(id).get();
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }

    public Note addGroup(Note targetNote) {
        Note note = new Note();
        note.setTitle("하위노트");
        note.setCreateDate(LocalDateTime.now());
        note.setParent(targetNote);
        noteRepository.save(note);
        return note;
    }

    public void modifyNote(Note note, String title) {
        note.setTitle(title);
        noteRepository.save(note);
    }
}
