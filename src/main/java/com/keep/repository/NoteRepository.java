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

    private Connection connection;

    public NoteRepository() {
        connection = DatabaseConnection.getConnection();
    }

    public NoteRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Note note) {

        if (connection == null) {
            System.err.println("Database connection failed.");
            return;
        }

        String sql = """
                INSERT INTO notes
                (title, content, pinned, archived, deleted,
                 color, label, reminder_at,
                 created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());

            statement.setBoolean(3, note.isPinned());
            statement.setBoolean(4, note.isArchived());
            statement.setBoolean(5, note.isDeleted());

            statement.setString(6, note.getColor());
            statement.setString(7, note.getLabel());

            if (note.getReminderAt() == null) {
                statement.setString(8, null);
            } else {
                statement.setString(8,
                        note.getReminderAt().toString());
            }

            statement.setString(9,
                    note.getCreatedAt().toString());

            statement.setString(10,
                    note.getUpdatedAt().toString());

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to save note : "
                            + e.getMessage());

        }

    }

    public ArrayList<Note> getAllNotes() {

        ArrayList<Note> notes = new ArrayList<>();

        if (connection == null) {
            return notes;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE deleted = 0
                AND archived = 0
                ORDER BY pinned DESC,
                created_at DESC
                """;

        try (

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                notes.add(mapResultSet(resultSet));

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to retrieve notes : "
                            + e.getMessage());

        }

        return notes;

    }

    public Note getNoteById(int id) {

        if (connection == null) {
            return null;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet =
                    statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to get note : "
                            + e.getMessage());

        }

        return null;

    }

    public void update(Note note) {

        if (connection == null) {
            return;
        }

        String sql = """
                UPDATE notes
                SET title = ?,
                    content = ?,
                    pinned = ?,
                    archived = ?,
                    deleted = ?,
                    color = ?,
                    label = ?,
                    reminder_at = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            note.setUpdatedAt(LocalDateTime.now());

            statement.setString(1, note.getTitle());
            statement.setString(2, note.getContent());

            statement.setBoolean(3, note.isPinned());
            statement.setBoolean(4, note.isArchived());
            statement.setBoolean(5, note.isDeleted());

            statement.setString(6, note.getColor());
            statement.setString(7, note.getLabel());

            if (note.getReminderAt() == null) {
                statement.setString(8, null);
            } else {
                statement.setString(8,
                        note.getReminderAt().toString());
            }

            statement.setString(9,
                    note.getUpdatedAt().toString());

            statement.setInt(10,
                    note.getId());

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to update note : "
                            + e.getMessage());
        }

    }

    public void delete(int id) {

        if (id <= 0) {
            return;
        }

        String sql = """
                UPDATE notes
                SET deleted = 1,
                    updated_at = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setString(1,
                    LocalDateTime.now().toString());

            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to move note to trash : "
                            + e.getMessage());

        }

    }

    public ArrayList<Note> search(String keyword) {

        ArrayList<Note> notes = new ArrayList<>();

        if (connection == null) {
            return notes;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE deleted = 0
                AND archived = 0
                AND (
                    title LIKE ?
                    OR content LIKE ?
                    OR label LIKE ?
                )
                ORDER BY pinned DESC,
                created_at DESC
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            String text = "%" + keyword + "%";

            statement.setString(1, text);
            statement.setString(2, text);
            statement.setString(3, text);

            try (ResultSet resultSet =
                    statement.executeQuery()) {

                while (resultSet.next()) {

                    notes.add(mapResultSet(resultSet));

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to search note : "
                            + e.getMessage());
        }

        return notes;

    }

    public ArrayList<Note> searchByLabel(String label) {

        ArrayList<Note> notes = new ArrayList<>();

        if (connection == null) {
            return notes;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE deleted = 0
                AND label LIKE ?
                ORDER BY pinned DESC,
                created_at DESC
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setString(1,
                    "%" + label + "%");

            try (ResultSet resultSet =
                    statement.executeQuery()) {

                while (resultSet.next()) {

                    notes.add(mapResultSet(resultSet));

                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to search label : "
                            + e.getMessage());

        }

        return notes;

    }

    public void pin(int id, boolean pinned) {

        if (connection == null) {
            return;
        }

        String sql = """
                UPDATE notes
                SET pinned = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setBoolean(1, pinned);

            statement.setString(2,
                    LocalDateTime.now().toString());

            statement.setInt(3, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to pin note : "
                            + e.getMessage());

        }

    }

    public void archive(int id, boolean archived) {

        if (connection == null) {
            return;
        }

        String sql = """
                UPDATE notes
                SET archived = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setBoolean(1, archived);

            statement.setString(2,
                    LocalDateTime.now().toString());

            statement.setInt(3, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to archive note : "
                            + e.getMessage());

        }

    }

    public ArrayList<Note> getArchivedNotes() {

        ArrayList<Note> notes = new ArrayList<>();

        if (connection == null) {
            return notes;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE archived = 1
                AND deleted = 0
                ORDER BY pinned DESC,
                created_at DESC
                """;

        try (

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                notes.add(mapResultSet(resultSet));

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to retrieve archived notes : "
                            + e.getMessage());

        }

        return notes;

    }

    public ArrayList<Note> getDeletedNotes() {

        ArrayList<Note> notes = new ArrayList<>();

        if (connection == null) {
            return notes;
        }

        String sql = """
                SELECT *
                FROM notes
                WHERE deleted = 1
                ORDER BY updated_at DESC
                """;

        try (

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()

        ) {

            while (resultSet.next()) {

                notes.add(mapResultSet(resultSet));

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to retrieve trash : "
                            + e.getMessage());

        }

        return notes;

    }

    public void restore(int id) {

        if (connection == null) {
            return;
        }

        String sql = """
                UPDATE notes
                SET deleted = 0,
                    archived = 0,
                    updated_at = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setString(1,
                    LocalDateTime.now().toString());

            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to restore note : "
                            + e.getMessage());

        }

    }

    public void deletePermanent(int id) {

        if (connection == null) {
            return;
        }

        String sql = """
                DELETE FROM notes
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to delete permanently : "
                            + e.getMessage());

        }

    }

    private Note mapResultSet(ResultSet resultSet)
            throws SQLException {

        LocalDateTime reminder = null;

        String reminderText =
                resultSet.getString("reminder_at");

        if (reminderText != null &&
                !reminderText.isBlank()) {

            reminder = LocalDateTime.parse(reminderText);

        }

        return new Note(

                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("content"),

                resultSet.getBoolean("pinned"),
                resultSet.getBoolean("archived"),
                resultSet.getBoolean("deleted"),

                resultSet.getString("color"),
                resultSet.getString("label"),

                reminder,

                LocalDateTime.parse(
                        resultSet.getString("created_at")
                ),

                LocalDateTime.parse(
                        resultSet.getString("updated_at")
                )

        );

    }

}