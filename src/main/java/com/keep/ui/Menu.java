package com.keep.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.keep.model.Note;
import com.keep.service.NoteService;

public class Menu {

    private Scanner scanner;
    private NoteService service;

    public Menu() {
        scanner = new Scanner(System.in);
        service = new NoteService();
    }

    public void showMenu() {

        int choice;

        do {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              GOOGLE KEEP");
            System.out.println("========================================");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Edit Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Search Note");
            System.out.println("6. Pin / Unpin Note");
            System.out.println("7. Archive / Restore Note");
            System.out.println("8. View Archived Notes");
            System.out.println("9. View Trash");
            System.out.println("10. Restore Note");
            System.out.println("11. Delete Permanently");
            System.out.println("0. Exit");
            System.out.println("========================================");
            System.out.print("Choose menu : ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a number : ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addNote();
                    break;

                case 2:
                    viewNotes();
                    break;

                case 3:
                    editNote();
                    break;

                case 4:
                    deleteNote();
                    break;

                case 5:
                    searchNote();
                    break;

                case 6:
                    pinNote();
                    break;

                case 7:
                    archiveNote();
                    break;

                case 8:
                    viewArchivedNotes();
                    break;

                case 9:
                    viewTrash();
                    break;

                case 10:
                    restoreNote();
                    break;

                case 11:
                    deleteForever();
                    break;

                case 0:
                    System.out.println("\nThank you for using Google Keep CLI.");
                    break;

                default:
                    System.out.println("Invalid menu.");
            }

            if (choice != 0) {
                pause();
            }

        } while (choice != 0);
    }


    private void addNote() {

        System.out.println();
        System.out.println("============== ADD NOTE ==============");

        System.out.print("Title : ");
        String title = scanner.nextLine();

        System.out.print("Content : ");
        String content = scanner.nextLine();

        if (title.trim().isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.println();
        System.out.println("Choose Color");
        System.out.println("1. White");
        System.out.println("2. Yellow");
        System.out.println("3. Green");
        System.out.println("4. Blue");
        System.out.println("5. Pink");
        System.out.print("Choice : ");

        String color = "WHITE";

        switch (scanner.nextLine()) {

            case "2":
                color = "YELLOW";
                break;

            case "3":
                color = "GREEN";
                break;

            case "4":
                color = "BLUE";
                break;

            case "5":
                color = "PINK";
                break;
        }

        System.out.print("Label (example: Kuliah, Kerja, Pribadi) : ");
        String label = scanner.nextLine();

        if (label.isBlank()) {
            label = "General";
        }

        Note note = new Note(title, content);

        note.setColor(color);
        note.setLabel(label);

        if (service.addNote(note)) {
            System.out.println("Note added successfully.");
        } else {
            System.out.println("Failed to add note.");
        }
    }

    private void viewNotes() {

        ArrayList<Note> notes = service.getAllNotes();

        System.out.println();
        System.out.println("============== ALL NOTES ==============");

        if (notes.isEmpty()) {
            System.out.println("No notes available.");
            return;
        }

        for (Note note : notes) {
            displayNoteDetails(note);
        }
    }

    private void editNote() {

        System.out.println();
        System.out.println("============== EDIT NOTE ==============");

        int id = inputId();

        Note note = service.getNoteById(id);

        if (note == null) {
            System.out.println("Note not found.");
            return;
        }

        System.out.println("Current Title   : " + note.getTitle());
        System.out.println("Current Content : " + note.getContent());
        System.out.println("Current Label   : " + note.getLabel());
        System.out.println("Current Color   : " + note.getColor());

        System.out.println();

        System.out.print("New Title (Enter = skip) : ");
        String title = scanner.nextLine();

        System.out.print("New Content (Enter = skip) : ");
        String content = scanner.nextLine();

        System.out.print("New Label (Enter = skip) : ");
        String label = scanner.nextLine();

        System.out.println();
        System.out.println("Color");
        System.out.println("1. Keep current");
        System.out.println("2. White");
        System.out.println("3. Yellow");
        System.out.println("4. Green");
        System.out.println("5. Blue");
        System.out.println("6. Pink");
        System.out.print("Choice : ");

        String color = scanner.nextLine();

        if (!title.isBlank()) {
            note.setTitle(title);
        }

        if (!content.isBlank()) {
            note.setContent(content);
        }

        if (!label.isBlank()) {
            note.setLabel(label);
        }

        switch (color) {

            case "2":
                note.setColor("WHITE");
                break;

            case "3":
                note.setColor("YELLOW");
                break;

            case "4":
                note.setColor("GREEN");
                break;

            case "5":
                note.setColor("BLUE");
                break;

            case "6":
                note.setColor("PINK");
                break;
        }

        if (service.updateNote(note)) {
            System.out.println("Note updated.");
        } else {
            System.out.println("Failed to update note.");
        }
    }


    private void deleteNote() {

        System.out.println();
        System.out.println("============= DELETE NOTE =============");

        int id = inputId();

        Note note = service.getNoteById(id);

        if (note == null) {
            System.out.println("Note not found.");
            return;
        }

        if (service.deleteNote(id)) {
            System.out.println("Note moved to Trash.");
        } else {
            System.out.println("Failed.");
        }
    }


    private void searchNote() {

        System.out.println();
        System.out.println("============= SEARCH NOTE =============");

        System.out.print("Keyword : ");

        String keyword = scanner.nextLine();

        ArrayList<Note> notes = service.searchNotes(keyword);

        if (notes.isEmpty()) {
            System.out.println("No note found.");
            return;
        }

        for (Note note : notes) {
            displayNoteDetails(note);
        }
    }

    private void pinNote() {

        System.out.println();
        System.out.println("============= PIN / UNPIN NOTE =============");

        int id = inputId();

        Note note = service.getNoteById(id);

        if (note == null) {
            System.out.println("Note not found.");
            return;
        }

        boolean status = !note.isPinned();

        if (service.pinNote(id, status)) {

            if (status) {
                System.out.println("Note pinned.");
            } else {
                System.out.println("Note unpinned.");
            }

        } else {
            System.out.println("Failed.");
        }
    }

    private void archiveNote() {

        System.out.println();
        System.out.println("============= ARCHIVE / RESTORE =============");

        int id = inputId();

        Note note = service.getNoteById(id);

        if (note == null) {
            System.out.println("Note not found.");
            return;
        }

        boolean status = !note.isArchived();

        if (service.archiveNote(id, status)) {

            if (status) {
                System.out.println("Note archived.");
            } else {
                System.out.println("Note restored from archive.");
            }

        } else {
            System.out.println("Failed.");
        }
    }

    private void viewArchivedNotes() {

        System.out.println();
        System.out.println("============= ARCHIVED NOTES =============");

        ArrayList<Note> notes = service.getArchivedNotes();

        if (notes.isEmpty()) {
            System.out.println("No archived notes.");
            return;
        }

        for (Note note : notes) {
            displayNoteDetails(note);
        }
    }

    private void viewTrash() {

        System.out.println();
        System.out.println("============= TRASH =============");

        ArrayList<Note> notes = service.getDeletedNotes();

        if (notes.isEmpty()) {
            System.out.println("Trash is empty.");
            return;
        }

        for (Note note : notes) {
            displayNoteDetails(note);
        }
    }

    private void displayNoteDetails(Note note) {

        System.out.println("----------------------------------------");
        System.out.println("ID       : " + note.getId());
        System.out.println("Title    : " + note.getTitle());
        System.out.println("Content  : " + note.getContent());
        System.out.println("Color    : " + note.getColor());
        System.out.println("Label    : " + note.getLabel());
        System.out.println("Pinned   : " + (note.isPinned() ? "Yes" : "No"));
        System.out.println("Archived : " + (note.isArchived() ? "Yes" : "No"));
        System.out.println("Created  : " + note.getCreatedAt());
        System.out.println("Updated  : " + note.getUpdatedAt());
    }

    private void restoreNote() {

        System.out.println();
        System.out.println("============= RESTORE NOTE =============");

        ArrayList<Note> notes = service.getDeletedNotes();

        if (notes.isEmpty()) {
            System.out.println("Trash is empty.");
            return;
        }

        for (Note note : notes) {
            displayNoteDetails(note);
        }

        int id = inputId();

        Note note = service.getNoteById(id);

        if (note == null) {
            System.out.println("Note not found.");
            return;
        }

        service.restoreNote(id);

        System.out.println("Note restored successfully.");
    }

    private void deleteForever() {

        System.out.println();
        System.out.println("============= DELETE PERMANENTLY =============");

        ArrayList<Note> notes = service.getDeletedNotes();

        if (notes.isEmpty()) {
            System.out.println("Trash is empty.");
            return;
        }

        for (Note note : notes) {
            displayNoteDetails(note);
        }

        int id = inputId();

        System.out.print("Are you sure delete permanently? (Y/N) : ");

        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            service.deletePermanent(id);

            System.out.println("Note permanently deleted.");

        } else {

            System.out.println("Cancelled.");

        }
    }

    private int inputId() {

        System.out.print("Enter Note ID : ");

        while (!scanner.hasNextInt()) {

            System.out.print("Please enter number : ");
            scanner.next();

        }

        int id = scanner.nextInt();
        scanner.nextLine();

        return id;
    }

    private void pause() {

        System.out.println();
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();

    }

}