package Experiments.employeeManagement;

import java.io.*;
import java.util.*;

public class EmployeeManagementSystem {
    private static List<employeeManagement> employees = new ArrayList<>();
    private static final String fileName = "employees.dat";
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Employee Management System ===");
        
        // Load existing employees from file
        loadEmployees();
        
        while (true) {
            showMenu();
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    saveEmployees();
                    System.out.println("Thank you for using Employee Management System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    public static void showMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Exit");
        System.out.print("Choose option (1-6): ");
    }
    
    public static void addEmployee() {
        System.out.println("\n=== Add New Employee ===");
        employeeManagement emp = employeeManagement.getEmployeeDetail();
        employees.add(emp);
        System.out.println("Employee added successfully!");
    }
    
    public static void viewAllEmployees() {
        System.out.println("\n=== All Employees ===");
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
        } else {
            for (employeeManagement emp : employees) {
                System.out.println(emp);
            }
        }
    }
    
    public static void searchEmployee() {
        System.out.println("\n=== Search Employee ===");
        System.out.print("Enter Employee ID to search: ");
        int searchId = sc.nextInt();
        
        boolean found = false;
        for (employeeManagement emp : employees) {
            if (emp.getId() == searchId) {
                System.out.println("Employee found: " + emp);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Employee with ID " + searchId + " not found!");
        }
    }
    
    public static void updateEmployee() {
        System.out.println("\n=== Update Employee ===");
        System.out.print("Enter Employee ID to update: ");
        int updateId = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        boolean found = false;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == updateId) {
                System.out.println("Current employee: " + employees.get(i));
                System.out.println("Enter new details:");
                
                employeeManagement newEmp = employeeManagement.getEmployeeDetail();
                employees.set(i, newEmp);
                System.out.println("Employee updated successfully!");
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Employee with ID " + updateId + " not found!");
        }
    }
    
    public static void deleteEmployee() {
        System.out.println("\n=== Delete Employee ===");
        System.out.print("Enter Employee ID to delete: ");
        int deleteId = sc.nextInt();
        
        boolean found = false;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == deleteId) {
                System.out.println("Deleting: " + employees.get(i));
                employees.remove(i);
                System.out.println("Employee deleted successfully!");
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Employee with ID " + deleteId + " not found!");
        }
    }
    
    public static void saveEmployees() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(employees);
            objectOut.close();
            fileOut.close();
            System.out.println("Employees saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }
    
    public static void loadEmployees() {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            employees = (List<employeeManagement>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Employees loaded from file successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing employee file found. Starting with empty list.");
            employees = new ArrayList<>();
        }
    }
}
