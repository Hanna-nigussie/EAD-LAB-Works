package com.todoapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DBConnectionManager {
    private Connection connection;

    public void openConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/TodoDB";
                String user = "root"; // Replace with your MySQL username
                String password = "122612"; // Replace with your MySQL password
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection.");
            e.printStackTrace();
        }
    }
}
