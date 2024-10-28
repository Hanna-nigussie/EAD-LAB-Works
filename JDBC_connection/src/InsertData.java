
//Task-2

import java.sql.*;

public class InsertData {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/student";
            String username ="root";
            String password = "Hello@world1234!";

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            Object[][] insertStatements = {
                {1, "Frank", "Smith", 90},
                {2, "Jane", "Johnson", 76},
                {3, "Charlie", "Taylor", 82},
                {4, "John", "Clark", 78},
                {5, "Ivy", "Davis", 85},
                {6, "Bob", "Wilson", 92},
                {7, "Hank", "King", 88},
                {8, "Emily", "White", 95},
                {9, "Grace", "Brown", 89},
                {10, "Alice", "Doe", 70}
            };

            for (Object[] student : insertStatements) {
                String insertStatement = String.format("INSERT INTO students (id, firstname,lastname,grade) VALUE(%d , '%s' , '%s' , %d)",(int)student[0],(String)student[1],(String)student[2],(int)student[3]);
                statement.executeUpdate(insertStatement);

            }
            System.out.println("Data inserted successfully!");

            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
