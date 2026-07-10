package com.keep.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {


    public static void initializeDatabase() {

        Connection connection = DatabaseConnection.getConnection();


        if (connection == null) {

            System.out.println("Failed to initialize database.");
            return;

        }


        String sql = """
                CREATE TABLE IF NOT EXISTS notes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    content TEXT,
                    pinned INTEGER DEFAULT 0,
                    archived INTEGER DEFAULT 0,
                    deleted INTEGER DEFAULT 0,
                    color TEXT,
                    label TEXT,
                    created_at TEXT,
                    updated_at TEXT
                );
                """;


        try {

            Statement statement = connection.createStatement();

            statement.execute(sql);


            statement.close();
            connection.close();


        } catch (SQLException e) {

            System.out.println("Failed to create table.");
            e.printStackTrace();

        }

    }

}