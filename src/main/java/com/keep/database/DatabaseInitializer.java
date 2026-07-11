package com.keep.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {

        String sql = """
                CREATE TABLE IF NOT EXISTS notes (

                    id INTEGER PRIMARY KEY AUTOINCREMENT,

                    title TEXT NOT NULL,
                    content TEXT,

                    pinned INTEGER DEFAULT 0,
                    archived INTEGER DEFAULT 0,
                    deleted INTEGER DEFAULT 0,

                    color TEXT DEFAULT 'WHITE',
                    label TEXT DEFAULT 'General',

                    reminder_at TEXT,

                    created_at TEXT,
                    updated_at TEXT

                );
                """;

        try {

            Connection connection = DatabaseConnection.getConnection();

            if (connection == null) {
                System.err.println("Database connection failed.");
                return;
            }

            Statement statement = connection.createStatement();

            statement.execute(sql);

            System.out.println("Database initialized successfully.");

            statement.close();
            connection.close();

        } catch (SQLException e) {

            System.err.println(
                    "Failed to create database : "
                    + e.getMessage());

        }
    }
}