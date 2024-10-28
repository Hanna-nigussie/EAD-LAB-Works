
//TASK-3

import java.sql.*;

public class RetriveData {
    public static void main(String[] args){
        try {
            String url = "jdbc:mysql://localhost:3306/student";
            String username = "root";
            String password = "Hello@world1234!";
            Connection connection = DriverManager.getConnection(url,username, password);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM STUDENTS LIMIT 5";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                int id = res.getInt("id");
                String fname = res.getString("firstname");
                String lname = res.getString("lastname");
                int grade = res.getInt("grade");

                System.out.println("Student id: " + id);
                System.out.println("Student full name: " + fname + lname);
                System.out.println("Student grade: " + grade );
                System.out.println("----------//----------");
            }
        
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }
    }
}
