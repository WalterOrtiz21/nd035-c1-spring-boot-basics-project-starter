package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.File;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServices {

    private final NoteMapper noteMapper;

    public NoteServices(NoteMapper noteMaper) {
        this.noteMapper = noteMaper;
    }

    public int createNote(Note note) {
        System.out.println("Vamos a agregar la nota");
        return noteMapper.insert(note);
    }

    public int updateNote(Note note) {
        System.out.println("Vamos a agregar la nota");
        return noteMapper.update(note);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public Note getNote(Integer fileId) {
        return noteMapper.getNote(fileId);
    }

    public int deleteNote(Integer fileId) {
       return noteMapper.delete(fileId);
    }

    public boolean isNoteTitleAvailable(String noteTitle) {
        return noteMapper.getNoteBytitle(noteTitle) == null;
    }
}
