package PartC.Controller;

import PartC.Model.Student;
import PartC.View.StudentView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller Layer - StudentController Class
 * Handles all database operations and business logic in MVC architecture
 */
public class StudentController {
    
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Vikesh21@";
    
    private StudentView view;
    private Connection connection;
    
    public StudentController(StudentView view) {
        this.view = view;
        this.connection = null;
    }
    
    // Initialize database connection
    public boolean initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false); // Enable transaction management
            view.showConnectionStatus(true);
            return true;
        } catch (ClassNotFoundException e) {
            view.showErrorMessage("MySQL JDBC Driver not found: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            view.showErrorMessage("Database connection failed: " + e.getMessage());
            return false;
        }
    }
    
    // Add new student
    public void addStudent() {
        try {
            Student student = view.getStudentInput();
            
            String insertQuery = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, student.getName());
                preparedStatement.setString(2, student.getDepartment());
                preparedStatement.setDouble(3, student.getMarks());
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Get the generated student ID
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            student.setStudentID(generatedKeys.getInt(1));
                        }
                    }
                    connection.commit();
                    view.showSuccessMessage("Student added with ID: " + student.getStudentID());
                } else {
                    connection.rollback();
                    view.showErrorMessage("Failed to add student");
                }
            }
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                view.showErrorMessage("Error during rollback: " + rollbackException.getMessage());
            }
            view.showErrorMessage("Database error: " + e.getMessage());
        }
    }
    
    // View all students
    public void viewAllStudents() {
        try {
            String selectQuery = "SELECT StudentID, Name, Department, Marks FROM Student ORDER BY StudentID";
            List<Student> students = new ArrayList<>();
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                
                while (resultSet.next()) {
                    int studentID = resultSet.getInt("StudentID");
                    String name = resultSet.getString("Name");
                    String department = resultSet.getString("Department");
                    double marks = resultSet.getDouble("Marks");
                    
                    students.add(new Student(studentID, name, department, marks));
                }
            }
            
            view.displayAllStudents(students);
            
        } catch (SQLException e) {
            view.showErrorMessage("Error retrieving students: " + e.getMessage());
        }
    }
    
    // Update student
    public void updateStudent() {
        try {
            int studentID = view.getStudentID();
            
            // First check if student exists
            Student existingStudent = getStudentById(studentID);
            if (existingStudent == null) {
                view.showErrorMessage("Student with ID " + studentID + " not found!");
                return;
            }
            
            // Get updated details
            Student updatedStudent = view.getUpdatedStudentInput(studentID);
            
            String updateQuery = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, updatedStudent.getName());
                preparedStatement.setString(2, updatedStudent.getDepartment());
                preparedStatement.setDouble(3, updatedStudent.getMarks());
                preparedStatement.setInt(4, studentID);
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    connection.commit();
                    view.showSuccessMessage("Student updated successfully");
                } else {
                    connection.rollback();
                    view.showErrorMessage("Failed to update student");
                }
            }
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                view.showErrorMessage("Error during rollback: " + rollbackException.getMessage());
            }
            view.showErrorMessage("Database error: " + e.getMessage());
        }
    }
    
    // Delete student
    public void deleteStudent() {
        try {
            int studentID = view.getStudentID();
            
            // First check if student exists
            Student student = getStudentById(studentID);
            if (student == null) {
                view.showErrorMessage("Student with ID " + studentID + " not found!");
                return;
            }
            
            // Confirm deletion
            if (!view.confirmDeletion(student)) {
                System.out.println("Deletion cancelled.");
                return;
            }
            
            String deleteQuery = "DELETE FROM Student WHERE StudentID = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, studentID);
                
                int rowsAffected = preparedStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    connection.commit();
                    view.showSuccessMessage("Student deleted successfully");
                } else {
                    connection.rollback();
                    view.showErrorMessage("Failed to delete student");
                }
            }
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                view.showErrorMessage("Error during rollback: " + rollbackException.getMessage());
            }
            view.showErrorMessage("Database error: " + e.getMessage());
        }
    }
    
    // Search student by ID
    public void searchStudent() {
        try {
            int studentID = view.getStudentID();
            Student student = getStudentById(studentID);
            view.displayStudent(student);
            
        } catch (SQLException e) {
            view.showErrorMessage("Database error: " + e.getMessage());
        }
    }
    
    // Helper method to get student by ID
    private Student getStudentById(int studentID) throws SQLException {
        String selectQuery = "SELECT StudentID, Name, Department, Marks FROM Student WHERE StudentID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, studentID);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("StudentID");
                    String name = resultSet.getString("Name");
                    String department = resultSet.getString("Department");
                    double marks = resultSet.getDouble("Marks");
                    
                    return new Student(id, name, department, marks);
                }
            }
        }
        
        return null; // Student not found
    }
    
    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            view.showErrorMessage("Error closing connection: " + e.getMessage());
        }
    }
}
