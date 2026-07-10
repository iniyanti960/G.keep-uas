package com.keep.service;

import java.util.ArrayList;

import com.keep.model.Note;
import com.keep.repository.NoteRepository;

public class NoteService {

    private NoteRepository repository;


    public NoteService() {

        repository = new NoteRepository();

    }


    public void addNote(Note note) {

        repository.save(note);

    }


    public ArrayList<Note> getAllNotes() {

        return repository.getAllNotes();

    }


    public Note getNoteById(int id) {

        return repository.getNoteById(id);

    }


    public void updateNote(Note note) {

        repository.update(note);

    }

}