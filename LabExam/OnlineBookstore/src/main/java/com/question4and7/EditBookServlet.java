package com.question4and7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

@WebServlet("/editurl")
public class EditBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DBConnectionManager manager;
	private final String tableName = "Books";
	private final String query = "UPDATE " + tableName + " SET title = ?, author = ?, price = ? WHERE id = ?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		int id;
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		float price;

		// Validate and parse parameters
		try {
			id = Integer.parseInt(req.getParameter("id"));
			price = Float.parseFloat(req.getParameter("price"));
		} catch (NumberFormatException e) {
			pw.println("<h2>Invalid input for ID or price. Please try again.</h2>");
			return;
		}

		try (Connection con = manager.openConnection();
				PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setFloat(3, price);
			ps.setInt(4, id);

			int count = ps.executeUpdate();
			if (count == 1) {
				pw.println("<h2>Book Edited Successfully</h2>");
			} else {
				pw.println("<h2>Failed to Edit Book</h2>");
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
