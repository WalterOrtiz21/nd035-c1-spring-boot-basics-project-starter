package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public Note() {
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Note{");
        sb.append("noteId=").append(noteId);
        sb.append(", noteTitle='").append(noteTitle).append('\'');
        sb.append(", noteDescription='").append(noteDescription).append('\'');
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}

