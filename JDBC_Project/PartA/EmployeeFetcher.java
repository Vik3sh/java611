package PartA;

import java.sql.*;

/**
 * Part A: Basic JDBC Connection and Data Fetching
 * Connects to MySQL database and fetches all records from Employee table
 */
public class EmployeeFetcher {
    
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_project";
    private static final String URL_WITHOUT_DB = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Vikesh21@"; // Enter your MySQL root password here
    
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Step 1: Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ MySQL JDBC Driver loaded successfully");
            
            // Step 2: Create database if it doesn't exist
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✓ Connected to existing database successfully");
            } catch (SQLException e) {
                if (e.getErrorCode() == 1049) { // Database doesn't exist
                    System.out.println("Database doesn't exist, creating it...");
                    connection = DriverManager.getConnection(URL_WITHOUT_DB, USERNAME, PASSWORD);
                    Statement createDbStatement = connection.createStatement();
                    createDbStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS jdbc_project");
                    createDbStatement.executeUpdate("USE jdbc_project");
                    
                    // Create Employee table
                    createDbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS Employee (" +
                        "EmpID INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Name VARCHAR(100) NOT NULL, " +
                        "Salary DECIMAL(10,2) NOT NULL)");
                    
                    // Insert sample data
                    createDbStatement.executeUpdate("INSERT INTO Employee (Name, Salary) VALUES " +
                        "('John Doe', 50000.00), " +
                        "('Jane Smith', 60000.00), " +
                        "('Mike Johnson', 55000.00), " +
                        "('Sarah Wilson', 65000.00)");
                    
                    createDbStatement.close();
                    System.out.println("✓ Database and table created with sample data");
                } else {
                    throw e;
                }
            }
            
            // Step 3: Create statement object
            statement = connection.createStatement();
            System.out.println("✓ Statement object created");
            
            // Step 4: Execute SELECT query
            String selectQuery = "SELECT EmpID, Name, Salary FROM Employee";
            resultSet = statement.executeQuery(selectQuery);
            System.out.println("✓ Query executed successfully");
            
            // Step 5: Display results
            System.out.println("\n=== Employee Records ===");
            System.out.println("ID\tName\t\tSalary");
            System.out.println("--------------------------------");
            
            while (resultSet.next()) {
                int empID = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");
                
                System.out.printf("%d\t%-15s\t$%.2f%n", empID, name, salary);
            }
            
            System.out.println("\n✓ All employee records fetched and displayed successfully");
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: MySQL JDBC Driver not found");
            System.err.println("Make sure mysql-connector-java.jar is in classpath");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ SQL Error: " + e.getMessage());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 6: Close resources in reverse order
            try {
                if (resultSet != null) {
                    resultSet.close();
                    System.out.println("✓ ResultSet closed");
                }
                if (statement != null) {
                    statement.close();
                    System.out.println("✓ Statement closed");
                }
                if (connection != null) {
                    connection.close();
                    System.out.println("✓ Connection closed");
                }
            } catch (SQLException e) {
                System.err.println("✗ Error closing resources: " + e.getMessage());
            }
        }
    }
}
