create database BookstoreDB;
use BookstoreDB;
create table tasks (
	id int auto_increment primary key, 
	title varchar(255), 
	author VARCHAR(255), 
	price double
);
