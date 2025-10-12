package PartC.Model;

/**
 * Model Layer - Student Class
 * Represents the student data structure in MVC architecture
 */
public class Student {
    
    // Student fields
    private int studentID;
    private String name;
    private String department;
    private double marks;
    
    // Default constructor
    public Student() {
        this.studentID = 0;
        this.name = "";
        this.department = "";
        this.marks = 0.0;
    }
    
    // Parameterized constructor
    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
    
    // Constructor without ID (for new students)
    public Student(String name, String department, double marks) {
        this.studentID = 0; // Will be set by database auto-increment
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
    
    // Getter methods
    public int getStudentID() {
        return studentID;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public double getMarks() {
        return marks;
    }
    
    // Setter methods
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public void setMarks(double marks) {
        this.marks = marks;
    }
    
    // toString method for display
    @Override
    public String toString() {
        return String.format("Student{ID=%d, Name='%s', Department='%s', Marks=%.2f}", 
                           studentID, name, department, marks);
    }
    
    // Method to get formatted display for table
    public String getFormattedDisplay() {
        return String.format("%-5d %-20s %-15s %.2f", 
                           studentID, name, department, marks);
    }
}
