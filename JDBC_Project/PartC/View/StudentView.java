package PartC.View;

import PartC.Model.Student;
import java.util.List;
import java.util.Scanner;

/**
 * View Layer - StudentView Class
 * Handles all user interface and input/output operations in MVC architecture
 */
public class StudentView {
    
    private Scanner scanner;
    
    public StudentView() {
        this.scanner = new Scanner(System.in);
    }
    
    // Display main menu
    public void showMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student by ID");
        System.out.println("6. Exit");
        System.out.print("Choose option (1-6): ");
    }
    
    // Get menu choice from user
    public int getMenuChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return choice;
    }
    
    // Get student details from user
    public Student getStudentInput() {
        System.out.println("\n=== Add New Student ===");
        
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        
        return new Student(name, department, marks);
    }
    
    // Get student ID for operations
    public int getStudentID() {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return id;
    }
    
    // Get updated student details
    public Student getUpdatedStudentInput(int studentID) {
        System.out.println("\n=== Update Student ===");
        System.out.println("Student ID: " + studentID);
        
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter new Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter new Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        
        return new Student(studentID, name, department, marks);
    }
    
    // Display all students
    public void displayAllStudents(List<Student> students) {
        System.out.println("\n=== All Students ===");
        
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        
        // Display header
        System.out.println("ID    Name                 Department      Marks");
        System.out.println("--------------------------------------------------------");
        
        // Display each student
        for (Student student : students) {
            System.out.println(student.getFormattedDisplay());
        }
        
        System.out.println("\nTotal students: " + students.size());
    }
    
    // Display single student
    public void displayStudent(Student student) {
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("\n=== Student Details ===");
        System.out.println("ID: " + student.getStudentID());
        System.out.println("Name: " + student.getName());
        System.out.println("Department: " + student.getDepartment());
        System.out.println("Marks: " + student.getMarks());
    }
    
    // Display success messages
    public void showSuccessMessage(String operation) {
        System.out.println("✓ " + operation + " completed successfully!");
    }
    
    // Display error messages
    public void showErrorMessage(String message) {
        System.err.println("✗ Error: " + message);
    }
    
    // Display confirmation message
    public boolean confirmDeletion(Student student) {
        System.out.println("\n=== Confirm Deletion ===");
        System.out.println("Student to be deleted:");
        displayStudent(student);
        
        System.out.print("Are you sure you want to delete this student? (y/n): ");
        String confirmation = scanner.nextLine().toLowerCase();
        
        return confirmation.equals("y") || confirmation.equals("yes");
    }
    
    // Display database connection status
    public void showConnectionStatus(boolean connected) {
        if (connected) {
            System.out.println("✓ Connected to database successfully");
        } else {
            System.err.println("✗ Failed to connect to database");
        }
    }
    
    // Display goodbye message
    public void showGoodbyeMessage() {
        System.out.println("\nThank you for using Student Management System!");
        System.out.println("Goodbye!");
    }
    
    // Display invalid choice message
    public void showInvalidChoiceMessage() {
        System.out.println("Invalid choice! Please try again.");
    }
    
    // Close scanner
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}

