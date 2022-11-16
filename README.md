# Data_Entry_Application

This is a simple java program I created to practice Java GUI (Java Swing) and Java Database Connectivity.

#Instructions to follow
**- To Create MySQL Databse Run The Following Queries:**
- CREATE DATABASE students_data;
- USE students_data;
- CREATE TABLE student(
	student_id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(150) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(6) NOT NULL,
    department VARCHAR(50) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY(student_id)
);
