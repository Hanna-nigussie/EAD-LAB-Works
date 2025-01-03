package com.todoapp.servlet;

import com.todoapp.db.DBConnectionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerTask")
public class TaskRegistrationServlet extends HttpServlet {
    
    // Override the doPost method to handle POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get task details from the request
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String dueDate = request.getParameter("due_date");

        // Set up the response content type to be HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get database connection
        DBConnectionManager dbManager = new DBConnectionManager();
        Connection conn = dbManager.getConnection();

        // SQL query to insert a task
        String insertQuery = "INSERT INTO Tasks (description, status, due_date) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, description);
            ps.setString(2, status);
            ps.setString(3, dueDate);

            // Execute the insert query
            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h3>Task successfully added!</h3>");
            } else {
                out.println("<h3>Failed to add task. Please try again.</h3>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            // Close the connection
            dbManager.closeConnection(conn);
        }
    }
}
