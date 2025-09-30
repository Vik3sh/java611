package Experiments.employeeManagement;
import java.util.*;
import java.io.Serializable;

public class employeeManagement implements Serializable{
    private int id;
    private String name;
    private double salary;
    private String department;
    private static final long serialVersionUID = 1L;

    employeeManagement(){
        
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public employeeManagement(int id,String name, double salary, String department){
        this.id=id;
        this.name=name;
        this.salary=salary;
        this.department=department;
    }
    public static employeeManagement getEmployeeDetail(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter employee id: ");
        int id=sc.nextInt();sc.nextLine();
        System.out.println("enter name: ");
        String name=sc.nextLine();
        System.out.println("enter salary: ");
        double salary=sc.nextDouble();sc.nextLine();
        System.out.println("enter department: ");
        String department=sc.nextLine();
        sc.close();
        return new employeeManagement(id, name, salary,department);
    }

    public void setEmployeeID(int empID) {
        this.id = empID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setDepartment(String department){
        this.department=department;
    }
    @Override
        public String toString() {
            return "Employee{id=" + id + ", name='" + name + "', salary=" + salary + ", department='" + department + "'}";
        }

}