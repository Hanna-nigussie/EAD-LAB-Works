package com.question5;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

@WebServlet("/bookList")
public class DisplayBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DBConnectionManager manager;

    private static final String TABLE_NAME = "Books";
    private static final String SELECT_QUERY = "SELECT ID, title, author, price FROM " + TABLE_NAME;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        try {
            displayBooks(pw);
        } catch (SQLException | ClassNotFoundException e) {
            handleError(pw, e.getMessage());
        }
    }

    private void displayBooks(PrintWriter pw) throws SQLException, ClassNotFoundException {
        try (Connection con = manager.openConnection();
                PreparedStatement ps = con.prepareStatement(SELECT_QUERY)) {
            ResultSet rs = ps.executeQuery();
            pw.println("<table border='1' align='center'>");
            pw.println("<tr>");
            pw.println("<th>Book Id</th>");
            pw.println("<th>Book Title</th>");
            pw.println("<th>Book Author</th>");
            pw.println("<th>Book Price</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt("ID") + "</td>");
                pw.println("<td>" + rs.getString("title") + "</td>");
                pw.println("<td>" + rs.getString("author") + "</td>");
                pw.println("<td>" + rs.getFloat("price") + "</td>");
                pw.println("<td><a href='editScreen?id=" + rs.getInt("ID") + "'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id=" + rs.getInt("ID") + "'>Delete</a></td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        }
    }

    private void handleError(PrintWriter pw, String errorMessage) {
        pw.println("<h1>Error: " + errorMessage + "</h1>");
        pw.println("<a href='index.html'>Home</a>");
    }
}
