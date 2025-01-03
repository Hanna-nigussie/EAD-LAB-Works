package com.question10;

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

@WebServlet("/searchList")
public class SearchBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DBConnectionManager manager;
    private String tableName = "Books";
    private final String query = "SELECT ID, title, author, price FROM " + tableName + " WHERE title LIKE ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String title = req.getParameter("searchTerm");
        if (title == null || title.trim().isEmpty()) {
            title = "%";
        } else {
            title = "%" + title + "%";
        }

        try {
            try (Connection con = manager.openConnection();
                    PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, title);

                ResultSet rs = ps.executeQuery();

                pw.println("<table border='1' align='center'>");
                pw.println("<tr>");
                pw.println("<th>Task Id</th>");
                pw.println("<th>Task Title</th>");
                pw.println("<th>Task Author</th>");
                pw.println("<th>Task Price</th>");
                pw.println("<th>Edit</th>");
                pw.println("<th>Delete</th>");
                pw.println("</tr>");

                boolean recordsFound = false;
                while (rs.next()) {
                    recordsFound = true;
                    pw.println("<tr>");
                    pw.println("<td>" + rs.getInt(1) + "</td>");
                    pw.println("<td>" + rs.getString(2) + "</td>");
                    pw.println("<td>" + rs.getString(3) + "</td>");
                    pw.println("<td>" + rs.getFloat(4) + "</td>");
                    pw.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>Edit</a></td>");
                    pw.println("<td><a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a></td>");
                    pw.println("</tr>");
                }

                if (!recordsFound) {
                    pw.println("<tr><td colspan='6'>No books found matching your search term.</td></tr>");
                }
                pw.println("</table>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        pw.println("<a href='index.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
