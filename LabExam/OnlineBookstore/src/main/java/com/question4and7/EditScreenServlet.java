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
    private String tableName = "Books";
	private final String query = "SELECT title,author,price FROM " + tableName + " where id=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		int id = Integer.parseInt(req.getParameter("id"));
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		try (Connection con = manager.openConnection(); PreparedStatement ps = con.prepareStatement(query);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			pw.println("<form action='editurl?id=" + id + "' method='post'>");
			pw.println("<table align='center'>");
			pw.println("<tr>");
			pw.println("<td>Title</td>");
			pw.println("<td><input type='text' name='title' value='" + rs.getString(1) + "'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Author</td>");
			pw.println("<td><input type='text' name='author' value='" + rs.getString(2) + "'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Due Date</td>");
			pw.println("<td><input type='text' name='price' value='" + rs.getFloat(3) + "'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type='submit' value='Edit'></td>");
			pw.println("<td><input type='reset' value='cancel'></td>");
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");
		} catch (SQLException se) {
			se.printStackTrace();
			pw.println("<h1>" + se.getMessage() + "</h2>");
		} catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h2>");
		}
		pw.println("<a href='index.html'>Home</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
