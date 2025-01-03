package com.question4and7;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class BookRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String TABLE_NAME = "Books";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLE_NAME + " (title, author, price) VALUES (?, ?, ?)";

    private final DBConnectionManager manager;

    @Autowired
    public BookRegistrationServlet(DBConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String priceInput = request.getParameter("price");

            if (title == null || title.isEmpty() || author == null || author.isEmpty() || priceInput == null
                    || priceInput.isEmpty()) {
                out.println(
                        "<h2 style='color: red;'>All fields are required. Please fill out the form completely.</h2>");
                displayHomeAndListLinks(out);
                return;
            }

            float price;
            try {
                price = Float.parseFloat(priceInput);
            } catch (NumberFormatException e) {
                out.println("<h2 style='color: red;'>Invalid price format. Please enter a valid number.</h2>");
                displayHomeAndListLinks(out);
                return;
            }

            try (Connection connection = manager.openConnection();
                    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {

                statement.setString(1, title);
                statement.setString(2, author);
                statement.setFloat(3, price);

                int result = statement.executeUpdate();
                if (result == 1) {
                    out.println("<h2 style='color: green;'>Book added successfully!</h2>");
                } else {
                    out.println("<h2 style='color: red;'>Failed to add the book. Please try again later.</h2>");
                }

            } catch (SQLException e) {
                handleDatabaseError(out, e);
            }

            displayHomeAndListLinks(out);
        }
    }

    private void handleDatabaseError(PrintWriter out, SQLException exception) {
        out.println("<h2 style='color: red;'>A database error occurred. Please try again later.</h2>");
        out.println("<p>Error Details: " + exception.getMessage() + "</p>");
    }

    private void displayHomeAndListLinks(PrintWriter out) {
        out.println("<br><a href='index.html' class='btn btn-primary'>Return to Home</a>");
        out.println("<a href='bookList' class='btn btn-secondary'>View Book List</a>");
    }
}
