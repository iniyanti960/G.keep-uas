package com.keep;

import com.keep.database.DatabaseInitializer;
import com.keep.model.Note;
import com.keep.repository.NoteRepository;

public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        NoteRepository repository = new NoteRepository();

        Note note = new Note(
                "Belajar Java",
                "Membuat project Google Keep CLI"
        );

        repository.save(note);

        System.out.println("Program finished.");

    }
}