package com.keep.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to save note.");
            e.printStackTrace();

        }

    }


    public ArrayList<Note> getAllNotes() {

        ArrayList<Note> notes = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            return notes;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE deleted = 0
                ORDER BY pinned DESC, id DESC
                """;

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Note note = new Note();

                note.setId(resultSet.getInt("id"));
                note.setTitle(resultSet.getString("title"));
                note.setContent(resultSet.getString("content"));

                note.setPinned(resultSet.getBoolean("pinned"));
                note.setArchived(resultSet.getBoolean("archived"));
                note.setDeleted(resultSet.getBoolean("deleted"));

                note.setColor(resultSet.getString("color"));
                note.setLabel(resultSet.getString("label"));

                note.setCreatedAt(
                        LocalDateTime.parse(resultSet.getString("created_at"))
                );

                note.setUpdatedAt(
                        LocalDateTime.parse(resultSet.getString("updated_at"))
                );

                notes.add(note);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to retrieve notes.");
            e.printStackTrace();

        }

        return notes;

    }


    public Note getNoteById(int id) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            return null;
        }

        String sql = "SELECT * FROM notes WHERE id = ?";


        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {

                Note note = new Note();

                note.setId(resultSet.getInt("id"));
                note.setTitle(resultSet.getString("title"));
                note.setContent(resultSet.getString("content"));

                note.setPinned(resultSet.getBoolean("pinned"));
                note.setArchived(resultSet.getBoolean("archived"));
                note.setDeleted(resultSet.getBoolean("deleted"));

                note.setColor(resultSet.getString("color"));
                note.setLabel(resultSet.getString("label"));

                note.setCreatedAt(
                        LocalDateTime.parse(resultSet.getString("created_at"))
                );

                note.setUpdatedAt(
                        LocalDateTime.parse(resultSet.getString("updated_at"))
                );


                resultSet.close();
                statement.close();
                connection.close();

                return note;

            }

            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {

            System.out.println("Failed to find note.");
            e.printStackTrace();

        }

        return null;

    }


    public void update(Note note) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            return;
        }


        String sql = """
                UPDATE notes
                SET title = ?, content = ?, updated_at = ?
                WHERE id = ?
                """;


        try {

            PreparedStatement statement = connection.prepareStatement(sql);


            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());
            statement.setString(3, LocalDateTime.now().toString());
            statement.setInt(4, note.getId());


            statement.executeUpdate();


            statement.close();
            connection.close();


        } catch (SQLException e) {

            System.out.println("Failed to update note.");
            e.printStackTrace();

        }

    }

    public void delete(int id) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection == null) {
            return;
        }

        String sql = "UPDATE notes SET deleted = 1 WHERE id = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println("Failed to delete note.");
            e.printStackTrace();

        }

    }

}