package com.question8;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DispatcherServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		String requestPath = path.substring(contextPath.length());

		// Log the requested path for debugging and monitoring purposes
		logger.info("Requested path: " + requestPath);

		// Handle various requests based on the URL path
		switch (requestPath) {
			case "/bookList":
				request.getRequestDispatcher("/bookList").forward(request, response);
				break;
			case "/index.html":
				request.getRequestDispatcher("/index.html").forward(request, response);
				break;
			case "/search.html":
				request.getRequestDispatcher("/search.html").forward(request, response);
				break;
			case "/addBook.html":
				request.getRequestDispatcher("/addBook.html").forward(request, response);
				break;
			case "/editScreen":
				request.getRequestDispatcher("/editScreen").forward(request, response);
				break;
			case "/editurl":
				request.getRequestDispatcher("/editurl").forward(request, response);
				break;
			default:
				// Handle invalid URL
				logger.warning("Invalid URL accessed: " + requestPath);
				response.getWriter().println("Invalid URL: " + requestPath);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				break;
		}
	}
}
