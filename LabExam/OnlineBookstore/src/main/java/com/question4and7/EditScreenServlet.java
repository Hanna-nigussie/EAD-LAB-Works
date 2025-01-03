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
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.question1and3.DBConnectionManager;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DBConnectionManager manager;

	private static final String TABLE_NAME = "Books";
	private static final String SELECT_QUERY = "SELECT title, author, price FROM " + TABLE_NAME + " WHERE id = ?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");

		try {
			int id = Integer.parseInt(req.getParameter("id"));
			displayEditForm(id, pw);
		} catch (NumberFormatException e) {
			handleError(pw, "Invalid book ID format.");
		} catch (SQLException | ClassNotFoundException e) {
			handleError(pw, "Database error: " + e.getMessage());
		}
	}

	private void displayEditForm(int id, PrintWriter pw) throws SQLException, ClassNotFoundException {
		try (Connection con = manager.openConnection();
				PreparedStatement ps = con.prepareStatement(SELECT_QUERY)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pw.println("<form action='editurl?id=" + id + "' method='post'>");
				pw.println("<table align='center'>");
				pw.println("<tr><td>Title</td><td><input type='text' name='title' value='" + rs.getString(1)
						+ "'></td></tr>");
				pw.println("<tr><td>Author</td><td><input type='text' name='author' value='" + rs.getString(2)
						+ "'></td></tr>");
				pw.println("<tr><td>Price</td><td><input type='text' name='price' value='" + rs.getFloat(3)
						+ "'></td></tr>");
				pw.println(
						"<tr><td><input type='submit' value='Edit'></td><td><input type='reset' value='Cancel'></td></tr>");
				pw.println("</table>");
				pw.println("</form>");
			} else {
				handleError(pw, "Book not found with the given ID.");
			}
		}
	}

	private void handleError(PrintWriter pw, String message) {
		pw.println("<h1>Error: " + message + "</h1>");
		pw.println("<a href='index.html'>Home</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
