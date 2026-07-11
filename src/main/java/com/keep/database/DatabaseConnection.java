package com.keep.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:notes.db";

    public static Connection getConnection() {

        try {

            Class.forName("org.sqlite.JDBC");

            return DriverManager.getConnection(URL);

        } catch (ClassNotFoundException e) {

            System.err.println("SQLite JDBC Driver not found.");

        } catch (SQLException e) {

            System.err.println(
                    "Failed to connect database : "
                    + e.getMessage());

        }

        return null;
    }
}