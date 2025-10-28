-- MySQL Database Setup for JDBC Project
-- Run these commands in MySQL Workbench or MySQL command line

-- Create database
CREATE DATABASE IF NOT EXISTS jdbc_project;
USE jdbc_project;

-- Part A: Employee Table
CREATE TABLE IF NOT EXISTS Employee (
    EmpID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Salary DECIMAL(10,2) NOT NULL
);

-- Insert sample data for Part A
INSERT INTO Employee (Name, Salary) VALUES 
('John Doe', 50000.00),
('Jane Smith', 60000.00),
('Mike Johnson', 55000.00),
('Sarah Wilson', 65000.00);

-- Part B: Product Table
CREATE TABLE IF NOT EXISTS Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Quantity INT NOT NULL
);

-- Insert sample data for Part B
INSERT INTO Product (ProductName, Price, Quantity) VALUES 
('Laptop', 999.99, 10),
('Mouse', 25.50, 50),
('Keyboard', 75.00, 30),
('Monitor', 299.99, 15);

-- Part C: Student Table
CREATE TABLE IF NOT EXISTS Student (
    StudentID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL,
    Department VARCHAR(50) NOT NULL,
    Marks DECIMAL(5,2) NOT NULL
);

-- Insert sample data for Part C
INSERT INTO Student (Name, Department, Marks) VALUES 
('Alice Brown', 'Computer Science', 85.5),
('Bob Davis', 'Mathematics', 92.0),
('Carol Green', 'Physics', 78.5),
('David Lee', 'Computer Science', 88.0);

-- Show all tables
SHOW TABLES;

-- Verify data
SELECT * FROM Employee;
SELECT * FROM Product;
SELECT * FROM Student;

