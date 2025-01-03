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

    private final DBConnectionManager manager;
    private final String tableName = "Books";
    private final String query = "INSERT INTO " + tableName + " (title, author, price) VALUES (?, ?, ?)";

    @Autowired
    public BookRegistrationServlet(DBConnectionManager manager) {
        super();
        this.manager = manager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        String title = req.getParameter("title");
        String author = req.getParameter("author");
        float price;

        try {
            price = Float.parseFloat(req.getParameter("price"));
        } catch (NumberFormatException nfe) {
            pw.println("<h2>Invalid price format. Please try again.</h2>");
            return;
        }

        try (Connection con = manager.openConnection();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setFloat(3, price);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Book Registered Successfully</h2>");
            } else {
                pw.println("<h2>Failed to Register Book</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>Database Error: " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Unexpected Error: " + e.getMessage() + "</h2>");
        }

        pw.println("<a href='index.html'>Home</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'>Book List</a>");
    }
}
