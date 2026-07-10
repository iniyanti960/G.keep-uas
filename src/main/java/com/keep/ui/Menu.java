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
            System.out.println("             GOOGLE KEEP");
            System.out.println("========================================");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Edit Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Search Note");
            System.out.println("6. Pin Note");
            System.out.println("7. Archive Note");
            System.out.println("8. Exit");
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
                    pause();
                    break;

                case 2:
                    viewNotes();
                    pause();
                    break;

                case 3:
                    editNote();
                    pause();
                    break;

                case 4:
                    deleteNote();
                    pause();
                    break;

                case 5:
                    System.out.println("\nSearch Note feature is not available yet.");
                    pause();
                    break;

                case 6:
                    System.out.println("\nPin Note feature is not available yet.");
                    pause();
                    break;

                case 7:
                    System.out.println("\nArchive Note feature is not available yet.");
                    pause();
                    break;

                case 8:
                    System.out.println("\nThank you for using Google Keep CLI.");
                    break;

                default:
                    System.out.println("\nInvalid menu.");
                    pause();
            }

        } while (choice != 8);

    }


    private void addNote() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("                ADD NOTE");
        System.out.println("========================================");

        System.out.print("Title   : ");
        String title = scanner.nextLine().trim();

        System.out.print("Content : ");
        String content = scanner.nextLine().trim();

        if (title.isEmpty() && content.isEmpty()) {
            System.out.println();
            System.out.println("Title and Content cannot both be empty.");
            return;
        }

        Note note = new Note(title, content);

        service.addNote(note);

        System.out.println();
        System.out.println("✓ Note added successfully.");

    }


    private void viewNotes() {

        ArrayList<Note> notes = service.getAllNotes();

        System.out.println();
        System.out.println("========================================");
        System.out.println("               ALL NOTES");
        System.out.println("========================================");

        if (notes.isEmpty()) {
            System.out.println("No notes available.");
            return;
        }

        for (Note note : notes) {

            String color = note.getColor();

            if (color != null && !color.isEmpty()) {
                color = color.substring(0, 1).toUpperCase()
                        + color.substring(1).toLowerCase();
            }

            System.out.println("========================================");
            System.out.println("ID      : " + note.getId());
            System.out.println();
            System.out.println("Title   : " + note.getTitle());
            System.out.println("Content : " + note.getContent());
            System.out.println();
            System.out.println("Label   : " + note.getLabel());
            System.out.println("Color   : " + color);
            System.out.println("Pinned  : " + (note.isPinned() ? "Yes" : "No"));
            System.out.println("========================================");
            System.out.println();
        }

    }


    private void editNote() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("                EDIT NOTE");
        System.out.println("========================================");

        System.out.print("Input Note ID : ");

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number : ");
            scanner.next();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        Note note = service.getNoteById(id);

        if (note == null) {

            System.out.println();
            System.out.println("Note not found.");
            return;

        }

        System.out.println();
        System.out.println("Current Title   : " + note.getTitle());
        System.out.println("Current Content : " + note.getContent());

        System.out.println();

        System.out.print("New Title   : ");
        String title = scanner.nextLine().trim();

        System.out.print("New Content : ");
        String content = scanner.nextLine().trim();


        if (title.isEmpty()) {
            title = note.getTitle();
        }

        if (content.isEmpty()) {
            content = note.getContent();
        }

        note.setTitle(title);
        note.setContent(content);

        service.updateNote(note);

        System.out.println();
        System.out.println("✓ Note updated successfully.");

    }


    private void pause() {

        System.out.print("Press Enter to continue...");
        scanner.nextLine();

    }

    private void deleteNote() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("               DELETE NOTE");
        System.out.println("========================================");

        System.out.print("Input Note ID : ");

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number : ");
            scanner.next();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        Note note = service.getNoteById(id);

        if (note == null) {

            System.out.println();
            System.out.println("Note not found.");
            return;

        }

        service.deleteNote(id);

        System.out.println();
        System.out.println("✓ Note deleted successfully.");

    }
}