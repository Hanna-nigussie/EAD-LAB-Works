<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Book</title>
</head>
<body>
    <h1>Delete Book</h1>
    <form action="DeleteBookServlet" method="post">
        <label for="id">Enter Book ID to Delete:</label>
        <input type="number" id="id" name="id" required>
        <button type="submit">Delete Book</button>
    </form>
</body>
</html>
