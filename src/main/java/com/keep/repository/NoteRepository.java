package com.keep.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.keep.database.DatabaseConnection;
import com.keep.model.Note;

public class NoteRepository {

    public void save(Note note) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            System.out.println("Database connection failed.");
            return;
        }

        String sql = """
                INSERT INTO notes
                (title, content, pinned, archived, deleted, color, label, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());

            statement.setBoolean(3, note.isPinned());
            statement.setBoolean(4, note.isArchived());
            statement.setBoolean(5, note.isDeleted());

            statement.setString(6, note.getColor());
            statement.setString(7, note.getLabel());

            statement.setString(8, note.getCreatedAt().toString());
            statement.setString(9, note.getUpdatedAt().toString());

            statement.executeUpdate();

            System.out.println("Note saved successfully.");

            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to save note.");
            e.printStackTrace();

        }
    }
}