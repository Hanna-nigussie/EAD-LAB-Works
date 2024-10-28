import java.sql.*;

public class CalculateAverage {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "Hello@world1234!";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            String query = "SELECT AVG(grade) AS average_grade FROM students";

            ResultSet res = statement.executeQuery(query);

            if (res.next()) {
                double averageGrade = res.getInt("average_grade");
                System.out.println("Average Grade: " + averageGrade);

            }
        } catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }

    }
}
