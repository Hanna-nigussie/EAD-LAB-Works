package com.question1and3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class DBConnectionManager {
    private String databaseName = "BookstoreDB";
    private String tableName = "Books";
    private String loginTableName = "login";
    private String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private String username = "root";
    private String password = "hanna";

    public Connection openConnection() throws SQLException {
        createDatabase();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("Error opening connection: " + e.getMessage());
            throw e;
        }
        createTable(conn, tableName, getBooksTableCommand());
        createTable(conn, loginTableName, getLoginTableCommand());
        return conn;
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private void createDatabase() {
        String createDatabaseCommand = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String generalUrl = "jdbc:mysql://localhost:3306/";

        try (Connection connection = DriverManager.getConnection(generalUrl, username, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDatabaseCommand);
            System.out.println("Database " + databaseName + " created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    private void createTable(Connection connection, String tableName, String createTableCommand) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableCommand);
            System.out.println("Table " + tableName + " created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating table " + tableName + ": " + e.getMessage());
        }
    }

    private String getBooksTableCommand() {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "title VARCHAR(255),"
                + "author VARCHAR(255),"
                + "price DOUBLE)";
    }

    private String getLoginTableCommand() {
        return "CREATE TABLE IF NOT EXISTS " + loginTableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(255) UNIQUE,"
                + "password VARCHAR(255))";
    }
}
