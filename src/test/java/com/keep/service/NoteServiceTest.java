package com.keep.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.keep.model.Note;
import com.keep.repository.NoteRepository;


@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private NoteService service;


    @Test
    void addNote_success() {

        Note note = new Note(
                "Belajar Java",
                "Testing JUnit"
        );

        boolean result = service.addNote(note);

        assertTrue(result);
        verify(repository).save(note);
    }


    @Test
    void addNote_failed_emptyTitle() {

        Note note = new Note(
                "",
                "Content"
        );

        boolean result = service.addNote(note);

        assertFalse(result);
        verify(repository, never()).save(any());
    }


    @Test
    void addNote_failed_nullNote() {

        boolean result = service.addNote(null);

        assertFalse(result);
        verify(repository, never()).save(any());
    }


    @Test
    void getAllNotes_success() {

        ArrayList<Note> notes = new ArrayList<>();

        when(repository.getAllNotes())
                .thenReturn(notes);

        ArrayList<Note> result =
                service.getAllNotes();

        assertEquals(notes, result);

        verify(repository)
                .getAllNotes();
    }


    @Test
    void getNoteById_success() {

        Note note = new Note(
                1,
                "Test",
                "Content",
                false,
                false,
                false,
                "WHITE",
                "General",
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(repository.getNoteById(1))
                .thenReturn(note);


        Note result =
                service.getNoteById(1);


        assertNotNull(result);
        assertEquals("Test", result.getTitle());

        verify(repository)
                .getNoteById(1);
    }


    @Test
    void getNoteById_invalidId() {

        Note result =
                service.getNoteById(0);

        assertNull(result);

        verify(repository, never())
                .getNoteById(anyInt());
    }


    @Test
    void updateNote_success() {

        Note note = new Note(
                "Update",
                "Content"
        );

        note.setId(1);


        boolean result =
                service.updateNote(note);


        assertTrue(result);

        verify(repository)
                .update(note);
    }


    @Test
    void updateNote_failed_emptyTitle() {

        Note note = new Note(
                "",
                "Content"
        );

        note.setId(1);


        boolean result =
                service.updateNote(note);


        assertFalse(result);

        verify(repository, never())
                .update(any());
    }


    @Test
    void deleteNote_success() {

        boolean result =
                service.deleteNote(1);


        assertTrue(result);

        verify(repository)
                .delete(1);
    }


    @Test
    void deleteNote_failed_invalidId() {

        boolean result =
                service.deleteNote(0);


        assertFalse(result);

        verify(repository, never())
                .delete(anyInt());
    }


    @Test
    void searchNotes_emptyKeyword() {

        ArrayList<Note> notes =
                new ArrayList<>();

        when(repository.getAllNotes())
                .thenReturn(notes);


        ArrayList<Note> result =
                service.searchNotes("");


        assertEquals(notes, result);

        verify(repository)
                .getAllNotes();
    }


    @Test
    void searchNotes_success() {

        ArrayList<Note> notes =
                new ArrayList<>();

        notes.add(
                new Note(
                        "Java",
                        "Learning"
                )
        );


        when(repository.search("Java"))
                .thenReturn(notes);


        ArrayList<Note> result =
                service.searchNotes("Java");


        assertEquals(1, result.size());

        verify(repository)
                .search("Java");
    }


    @Test
    void searchByLabel_success() {

        ArrayList<Note> notes =
                new ArrayList<>();

        notes.add(
                new Note(
                        "Kuliah",
                        "PBO"
                )
        );


        when(repository.searchByLabel("Kuliah"))
                .thenReturn(notes);


        ArrayList<Note> result =
                service.searchByLabel("Kuliah");


        assertEquals(1, result.size());

        verify(repository)
                .searchByLabel("Kuliah");
    }


    @Test
    void pinNote_success() {

        boolean result =
                service.pinNote(1, true);


        assertTrue(result);

        verify(repository)
                .pin(1, true);
    }


    @Test
    void archiveNote_success() {

        boolean result =
                service.archiveNote(1, true);


        assertTrue(result);

        verify(repository)
                .archive(1, true);
    }


    @Test
    void restoreNote_success() {

        service.restoreNote(1);

        verify(repository)
                .restore(1);
    }


    @Test
    void deletePermanent_success() {

        service.deletePermanent(1);

        verify(repository)
                .deletePermanent(1);
    }

}