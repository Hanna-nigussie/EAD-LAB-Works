package com.question6;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

@WebServlet("/deleteurl")
public class DeleteBookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DBConnectionManager manager;
    private static final String TABLE_NAME = "Books";
    private static final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        int id = Integer.parseInt(req.getParameter("id"));
        try {
            deleteBook(pw, id);
        } catch (SQLException | ClassNotFoundException e) {
            handleError(pw, e.getMessage());
        }
    }

    private void deleteBook(PrintWriter pw, int id) throws SQLException, ClassNotFoundException {
        try (Connection con = manager.openConnection();
                PreparedStatement ps = con.prepareStatement(DELETE_QUERY)) {
            ps.setInt(1, id);
            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<h2>Record Deleted Successfully</h2>");
            } else {
                pw.println("<h2>Record Not Deleted</h2>");
            }
        }
    }

    private void handleError(PrintWriter pw, String errorMessage) {
        pw.println("<h1>Error: " + errorMessage + "</h1>");
        pw.println("<a href='index.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
