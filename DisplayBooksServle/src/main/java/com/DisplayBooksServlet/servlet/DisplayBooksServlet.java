package com.bookstoreapp.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Define a Book class to hold book data
class Book {
    private int id;
    private String title;
    private String author;
    private double price;

    // Constructor
    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }
}

public class DisplayBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> booksList = new ArrayList<>();

        try {
            // Database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookstoreDB", "root", "122612");

            // Query to fetch all books
            String sql = "SELECT * FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through the result set and add each book to the list
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");

                booksList.add(new Book(id, title, author, price));
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass the list of books to the JSP page
        request.setAttribute("books", booksList);

        // Forward the request to displayBooks.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("displayBooks.jsp");
        dispatcher.forward(request, response);
    }
}
