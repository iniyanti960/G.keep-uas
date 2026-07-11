package com.keep.service;

import java.util.ArrayList;

import com.keep.model.Note;
import com.keep.repository.NoteRepository;

public class NoteService {

    private NoteRepository repository;

    public NoteService() {
        repository = new NoteRepository();
    }

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public boolean addNote(Note note) {

        if (note == null
                || note.getTitle() == null
                || note.getTitle().trim().isEmpty()) {

            System.err.println("Validation failed: Title cannot be empty.");
            return false;

        }

        repository.save(note);
        return true;

    }

    public ArrayList<Note> getAllNotes() {
        return repository.getAllNotes();
    }

    public ArrayList<Note> getArchivedNotes() {
        return repository.getArchivedNotes();
    }

    public ArrayList<Note> getDeletedNotes() {
        return repository.getDeletedNotes();
    }

    public Note getNoteById(int id) {

        if (id <= 0) {

            System.err.println("Validation failed: Invalid Note ID.");
            return null;

        }

        return repository.getNoteById(id);

    }

    public boolean updateNote(Note note) {

        if (note == null
                || note.getId() <= 0
                || note.getTitle() == null
                || note.getTitle().trim().isEmpty()) {

            System.err.println("Validation failed: Invalid note data.");
            return false;

        }

        repository.update(note);
        return true;

    }

    public boolean deleteNote(int id) {

        if (id <= 0) {

            System.err.println("Validation failed: Invalid Note ID.");
            return false;

        }

        repository.delete(id);
        return true;

    }

    public void restoreNote(int id) {

        if (id <= 0) {
            return;
        }

        repository.restore(id);

    }

    public void deletePermanent(int id) {

        if (id <= 0) {
            return;
        }

        repository.deletePermanent(id);

    }

    public ArrayList<Note> searchNotes(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.getAllNotes();
        }

        return repository.search(keyword.trim());

    }

    public ArrayList<Note> searchByLabel(String label) {

        if (label == null || label.trim().isEmpty()) {
            return repository.getAllNotes();
        }

        return repository.searchByLabel(label.trim());

    }

    public boolean pinNote(int id, boolean pinned) {

        if (id <= 0) {
            return false;
        }

        repository.pin(id, pinned);
        return true;

    }

    public boolean archiveNote(int id, boolean archived) {

        if (id <= 0) {
            return false;
        }

        repository.archive(id, archived);
        return true;

    }

}