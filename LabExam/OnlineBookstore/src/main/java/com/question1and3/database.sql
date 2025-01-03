CREATE DATABASE BookstoreDB;
USE BookstoreDB;
CREATE TABLE Books (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    title VARCHAR(255), 
    author VARCHAR(255), 
    price DOUBLE
);
