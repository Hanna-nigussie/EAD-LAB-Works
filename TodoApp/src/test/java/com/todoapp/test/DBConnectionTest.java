package com.todoapp.test;

import com.todoapp.db.DBConnectionManager;

public class DBConnectionTest {
    public static void main(String[] args) {
        DBConnectionManager dbManager = new DBConnectionManager();

        // Open Connection
        dbManager.openConnection();

        // Close Connection
        dbManager.closeConnection();
    }
}
