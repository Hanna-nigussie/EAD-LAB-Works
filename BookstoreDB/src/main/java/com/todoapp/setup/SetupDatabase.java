package com.todoapp.setup;

import com.todoapp.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;

public class SetupDatabase {
    public static void main(String[] args) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Tasks (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "description VARCHAR(255), " +
                "status VARCHAR(50), " +
                "due_date DATE" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Tasks table created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
