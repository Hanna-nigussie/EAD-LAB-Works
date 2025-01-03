package com.question4and7;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

@WebServlet("/editBook")
public class EditBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String TABLE_NAME = "Books";
    private static final String UPDATE_QUERY = "UPDATE " + TABLE_NAME
            + " SET title = ?, author = ?, price = ? WHERE id = ?";

    @Autowired
    private DBConnectionManager dbManager;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        try (PrintWriter writer = response.getWriter()) {
            
            BookDetails bookDetails = extractBookDetails(request, writer);
            if (bookDetails == null) {
                return; 
            }

            // Perform update
            boolean isUpdated = updateBookInDatabase(bookDetails);
            displayResponseMessage(writer, isUpdated, bookDetails.getId());
        }
    }

    private BookDetails extractBookDetails(HttpServletRequest request, PrintWriter writer) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String idParam = request.getParameter("id");
        String priceParam = request.getParameter("price");

        // Validate input
        if (title == null || author == null || idParam == null || priceParam == null
                || title.trim().isEmpty() || author.trim().isEmpty() || idParam.trim().isEmpty()
                || priceParam.trim().isEmpty()) {
            writer.println("<h2 style='color: red;'>All fields are required. Please try again.</h2>");
            displayNavigationLinks(writer);
            return null;
        }

        try {
            int id = Integer.parseInt(idParam);
            float price = Float.parseFloat(priceParam);
            return new BookDetails(id, title, author, price);
        } catch (NumberFormatException e) {
            writer.println(
                    "<h2 style='color: red;'>Invalid ID or price format. Please enter valid numeric values.</h2>");
            displayNavigationLinks(writer);
            return null;
        }
    }

    private boolean updateBookInDatabase(BookDetails bookDetails) {
        try (Connection connection = dbManager.openConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, bookDetails.getTitle());
            statement.setString(2, bookDetails.getAuthor());
            statement.setFloat(3, bookDetails.getPrice());
            statement.setInt(4, bookDetails.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void displayResponseMessage(PrintWriter writer, boolean isUpdated, int bookId) {
        if (isUpdated) {
            writer.println("<h2 style='color: green;'>Book with ID " + bookId + " has been updated successfully!</h2>");
        } else {
            writer.println(
                    "<h2 style='color: red;'>Failed to update the book. Book ID " + bookId + " may not exist.</h2>");
        }
        displayNavigationLinks(writer);
    }

    private void displayNavigationLinks(PrintWriter writer) {
        writer.println("<br><a href='index.html'>Home</a>");
        writer.println("<br><a href='bookList'>View Book List</a>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // Inner class for encapsulating book details
    private static class BookDetails {
        private final int id;
        private final String title;
        private final String author;
        private final float price;

        public BookDetails(int id, String title, String author, float price) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public float getPrice() {
            return price;
        }
    }
}
