# JDBC Project - MySQL Database Connectivity

## Prerequisites

### 1. MySQL Database Setup
- Install MySQL Server
- Run `DatabaseSetup.sql` to create database and tables
- Default database: `jdbc_project`
- Default user: `root`
- Default password: `password` (change in code if different)

### 2. MySQL Connector JAR
- Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
- Place `mysql-connector-java-8.0.33.jar` in the project root
- Or use Maven/Gradle for dependency management

### 3. Compilation and Execution
```bash
# Compile with MySQL connector
javac -cp "mysql-connector-java-8.0.33.jar;." *.java

# Run the programs
java -cp "mysql-connector-java-8.0.33.jar;." PartA.EmployeeFetcher
java -cp "mysql-connector-java-8.0.33.jar;." PartB.ProductManager
java -cp "mysql-connector-java-8.0.33.jar;." PartC.StudentManagementSystem
```

## Project Structure

```
JDBC_Project/
├── PartA/                    # Basic JDBC Connection
│   └── EmployeeFetcher.java
├── PartB/                    # CRUD Operations with Transactions
│   └── ProductManager.java
├── PartC/                    # MVC Architecture
│   ├── Model/
│   │   └── Student.java
│   ├── View/
│   │   └── StudentView.java
│   ├── Controller/
│   │   └── StudentController.java
│   └── StudentManagementSystem.java
├── DatabaseSetup.sql
└── README.md
```

## Database Configuration

Update connection details in each Java file:
- URL: `jdbc:mysql://localhost:3306/jdbc_project`
- Username: `root`
- Password: `password` (change as needed)

