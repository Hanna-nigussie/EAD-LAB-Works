//TASK-1
import java.sql.*;

public class ConnectionSetUp{
    public static void main(String[] args) {
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/";
            String username = "root";
            String password = "Hello@world1234!";
            String databaseName = "student";


            Class.forName(driver);

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established!");

            Statement statement = connection.createStatement();

            String createDatabaseSQL = "CREATE DATABASE " + databaseName;

            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database " + databaseName + " created successfuly!" );


            statement.close();
            connection.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}