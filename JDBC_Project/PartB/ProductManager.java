package PartB;

import java.sql.*;
import java.util.Scanner;

/**
 * Part B: CRUD Operations on Product Table with Transaction Handling
 * Menu-driven program for Create, Read, Update, Delete operations
 */
public class ProductManager {
    
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Vikesh21@";
    
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection = null;
    
    public static void main(String[] args) {
        try {
            // Establish database connection
            establishConnection();
            
            // Main menu loop
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        createProduct();
                        break;
                    case 2:
                        readAllProducts();
                        break;
                    case 3:
                        updateProduct();
                        break;
                    case 4:
                        deleteProduct();
                        break;
                    case 5:
                        System.out.println("Thank you for using Product Manager!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    
    private static void establishConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false); // Enable transaction management
            System.out.println("✓ Connected to MySQL database with transaction support");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    private static void showMenu() {
        System.out.println("\n=== Product Management System ===");
        System.out.println("1. Create Product");
        System.out.println("2. Read All Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Exit");
        System.out.print("Choose option (1-5): ");
    }
    
    // CREATE Operation
    private static void createProduct() {
        try {
            System.out.println("\n=== Create New Product ===");
            
            System.out.print("Enter Product Name: ");
            String productName = scanner.nextLine();
            
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();
            
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            String insertQuery = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, productName);
                preparedStatement.setDouble(2, price);
                preparedStatement.setInt(3, quantity);
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    connection.commit(); // Commit transaction
                    System.out.println("✓ Product created successfully!");
                } else {
                    connection.rollback(); // Rollback on failure
                    System.out.println("✗ Failed to create product");
                }
            }
            
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("✗ Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackException) {
                System.err.println("✗ Error during rollback: " + rollbackException.getMessage());
            }
        }
    }
    
    // READ Operation
    private static void readAllProducts() {
        try {
            System.out.println("\n=== All Products ===");
            
            String selectQuery = "SELECT ProductID, ProductName, Price, Quantity FROM Product ORDER BY ProductID";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                
                if (!resultSet.next()) {
                    System.out.println("No products found!");
                    return;
                }
                
                System.out.println("ID\tName\t\t\tPrice\t\tQuantity");
                System.out.println("--------------------------------------------------------");
                
                do {
                    int productID = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("Price");
                    int quantity = resultSet.getInt("Quantity");
                    
                    System.out.printf("%d\t%-20s\t$%.2f\t\t%d%n", 
                                    productID, productName, price, quantity);
                } while (resultSet.next());
                
                System.out.println("\n✓ All products displayed successfully");
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error reading products: " + e.getMessage());
        }
    }
    
    // UPDATE Operation
    private static void updateProduct() {
        try {
            System.out.println("\n=== Update Product ===");
            
            System.out.print("Enter Product ID to update: ");
            int productID = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            // First, check if product exists
            String checkQuery = "SELECT * FROM Product WHERE ProductID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, productID);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        System.out.println("✗ Product with ID " + productID + " not found!");
                        return;
                    }
                    
                    // Display current product details
                    System.out.println("Current product details:");
                    System.out.println("Name: " + resultSet.getString("ProductName"));
                    System.out.println("Price: $" + resultSet.getDouble("Price"));
                    System.out.println("Quantity: " + resultSet.getInt("Quantity"));
                }
            }
            
            // Get new details
            System.out.print("Enter new Product Name: ");
            String newProductName = scanner.nextLine();
            
            System.out.print("Enter new Price: ");
            double newPrice = scanner.nextDouble();
            
            System.out.print("Enter new Quantity: ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            String updateQuery = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newProductName);
                preparedStatement.setDouble(2, newPrice);
                preparedStatement.setInt(3, newQuantity);
                preparedStatement.setInt(4, productID);
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    connection.commit(); // Commit transaction
                    System.out.println("✓ Product updated successfully!");
                } else {
                    connection.rollback(); // Rollback on failure
                    System.out.println("✗ Failed to update product");
                }
            }
            
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("✗ Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackException) {
                System.err.println("✗ Error during rollback: " + rollbackException.getMessage());
            }
        }
    }
    
    // DELETE Operation
    private static void deleteProduct() {
        try {
            System.out.println("\n=== Delete Product ===");
            
            System.out.print("Enter Product ID to delete: ");
            int productID = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            // First, check if product exists and show details
            String checkQuery = "SELECT * FROM Product WHERE ProductID = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, productID);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (!resultSet.next()) {
                        System.out.println("✗ Product with ID " + productID + " not found!");
                        return;
                    }
                    
                    // Display product details before deletion
                    System.out.println("Product to be deleted:");
                    System.out.println("ID: " + resultSet.getInt("ProductID"));
                    System.out.println("Name: " + resultSet.getString("ProductName"));
                    System.out.println("Price: $" + resultSet.getDouble("Price"));
                    System.out.println("Quantity: " + resultSet.getInt("Quantity"));
                    
                    System.out.print("Are you sure you want to delete this product? (y/n): ");
                    String confirmation = scanner.nextLine().toLowerCase();
                    
                    if (!confirmation.equals("y") && !confirmation.equals("yes")) {
                        System.out.println("Deletion cancelled.");
                        return;
                    }
                }
            }
            
            String deleteQuery = "DELETE FROM Product WHERE ProductID = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, productID);
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    connection.commit(); // Commit transaction
                    System.out.println("✓ Product deleted successfully!");
                } else {
                    connection.rollback(); // Rollback on failure
                    System.out.println("✗ Failed to delete product");
                }
            }
            
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("✗ Transaction rolled back due to error: " + e.getMessage());
            } catch (SQLException rollbackException) {
                System.err.println("✗ Error during rollback: " + rollbackException.getMessage());
            }
        }
    }
    
    private static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error closing connection: " + e.getMessage());
        }
    }
}
