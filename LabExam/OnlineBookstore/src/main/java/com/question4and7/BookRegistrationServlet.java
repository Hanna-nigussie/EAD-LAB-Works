package com.question4and7;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@WebServlet("/register")
public class BookRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DBConnectionManager manager;
    private String tableName = "Books";
    
    @Autowired
    public BookRegistrationServlet(DBConnectionManager manager) {
		super();
		this.manager = manager;
	}

	private final String query = "INSERT INTO " + tableName + " (title,author,price) VALUES(?,?,?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String title = req.getParameter("title");
        String author = req.getParameter("author");
        float price = Float.parseFloat(req.getParameter("price"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = manager.openConnection(); PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setFloat(3, price);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Book Is Registered Sucessfully</h2>");
            } else {
                pw.println("<h2>Book not Registered Sucessfully");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h2>");
        }
        pw.println("<a href='index.html'>Home</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'>Book List</a>");
    }
}
