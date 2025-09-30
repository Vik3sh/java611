package Experiments.experimentSerialization;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Student class that implements Serializable interface
 * This allows objects of this class to be serialized and deserialized
 */
public class Student implements Serializable {
    // Serial version UID for version control
    private static final long serialVersionUID = 1L;
    
    // Student fields
    private int studentID;
    private String name;
    private double grade;
    
    /**
     * Default constructor
     */
    public Student() {
        this.studentID = 0;
        this.name = "";
        this.grade = 0.0;
    }
    
    /**
     * Parameterized constructor
     * @param studentID The student's ID
     * @param name The student's name
     * @param grade The student's grade
     */
    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
    
    // Getter methods (return stored values)
    public int getStudentID() {
        return studentID;
    }
    
    public String getName() {
        return name;
    }
    
    public double getGrade() {
        return grade;
    }
    
    // Method to get input from user
    public static Student getStudentFromInput() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the student ID:");
        int id = sc.nextInt();
        sc.nextLine(); // Clear buffer
        
        System.out.println("Enter the name of the student:");
        String name = sc.nextLine();
        
        System.out.println("Enter the grade of the student:");
        double grade = sc.nextDouble();
        
        return new Student(id, name, grade);
    }
    
    // Setter methods
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setGrade(double grade) {
        this.grade = grade;
    }
    
    /**
     * Override toString method for better display
     */
    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}
