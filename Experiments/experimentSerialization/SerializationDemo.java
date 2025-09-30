package Experiments.experimentSerialization;

import java.io.*;
import java.util.Scanner;

/**
 * Demonstration of Java Serialization and Deserialization
 * This program shows how to save a Student object to a file and retrieve it
 */
public class SerializationDemo {
    
    public static void main(String[] args) {
        // File path where we'll save the serialized object
        String fileName = "student_data.ser";
        
        System.out.println("=== Java Serialization Demo ===\n");
        
        // Step 1: Create a Student object (you can choose input or hardcoded)
        System.out.println("Choose input method:");
        System.out.println("1. Use hardcoded data");
        System.out.println("2. Enter student data manually");
        
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        
        Student student;
        if (choice == 2) {
            student = Student.getStudentFromInput();
        } else {
            student = new Student(101, "John Doe", 85.5);
        }
        System.out.println("Original Student Object:");
        System.out.println(student);
        System.out.println();
        
        // Step 2: Serialize the Student object to a file
        try {
            serializeStudent(student, fileName);
            System.out.println(" Student object serialized successfully to " + fileName);
        } catch (IOException e) {
            System.err.println(" Error during serialization: " + e.getMessage());
            return;
        }
        
        System.out.println();
        
        // Step 3: Deserialize the Student object from the file
        try {
            Student deserializedStudent = deserializeStudent(fileName);
            System.out.println(" Student object deserialized successfully from " + fileName);
            System.out.println();
            System.out.println("Deserialized Student Object:");
            System.out.println(deserializedStudent);
            System.out.println();
            
            // Step 4: Verify the data integrity
            System.out.println("=== Data Verification ===");
            System.out.println("Student ID matches: " + (student.getStudentID() == deserializedStudent.getStudentID()));
            System.out.println("Name matches: " + student.getName().equals(deserializedStudent.getName()));
            System.out.println("Grade matches: " + (student.getGrade() == deserializedStudent.getGrade()));
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Error during deserialization: " + e.getMessage());
        }
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Serializes a Student object to a file
     * @param student The Student object to serialize
     * @param fileName The name of the file to save to
     * @throws IOException if there's an error writing to the file
     */
    public static void serializeStudent(Student student, String fileName) throws IOException {
        // Create FileOutputStream to write to file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        
        // Create ObjectOutputStream to serialize objects
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
        // Write the Student object to the stream
        objectOut.writeObject(student);
        
        // Close streams
        objectOut.close();
        fileOut.close();
    }
    
    /**
     * Deserializes a Student object from a file
     * @param fileName The name of the file to read from
     * @return The deserialized Student object
     * @throws IOException if there's an error reading from the file
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    public static Student deserializeStudent(String fileName) throws IOException, ClassNotFoundException {
        // Create FileInputStream to read from file
        FileInputStream fileIn = new FileInputStream(fileName);
        
        // Create ObjectInputStream to deserialize objects
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        
        // Read the Student object from the stream
        Student student = (Student) objectIn.readObject();
        
        // Close streams
        objectIn.close();
        fileIn.close();
        
        return student;
    }
}
