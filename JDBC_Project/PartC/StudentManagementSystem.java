package PartC;

import PartC.Controller.StudentController;
import PartC.View.StudentView;

/**
 * Main Application - Student Management System
 * Integrates Model, View, and Controller in MVC architecture
 */
public class StudentManagementSystem {
    
    public static void main(String[] args) {
        StudentView view = new StudentView();
        StudentController controller = new StudentController(view);
        
        try {
            // Initialize database connection
            if (!controller.initializeDatabase()) {
                System.err.println("Failed to initialize database. Exiting...");
                return;
            }
            
            System.out.println("=== Welcome to Student Management System ===");
            System.out.println("Using MVC Architecture with JDBC and MySQL");
            
            // Main application loop
            while (true) {
                view.showMenu();
                int choice = view.getMenuChoice();
                
                switch (choice) {
                    case 1:
                        controller.addStudent();
                        break;
                    case 2:
                        controller.viewAllStudents();
                        break;
                    case 3:
                        controller.updateStudent();
                        break;
                    case 4:
                        controller.deleteStudent();
                        break;
                    case 5:
                        controller.searchStudent();
                        break;
                    case 6:
                        view.showGoodbyeMessage();
                        return;
                    default:
                        view.showInvalidChoiceMessage();
                }
            }
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up resources
            controller.closeConnection();
            view.close();
        }
    }
}

